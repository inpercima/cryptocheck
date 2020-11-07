import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Settings } from '../features/settings/settings.model';
import { environment } from 'src/environments/environment';
import { CoinPrice } from '../features/coin-price/coin-price.model';
import { Constants } from './constants';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  assetPrices: any;

  assets: string[] = [];

  constructor(private http: HttpClient) {
    this.getAssets().subscribe(response => this.assets = response);
   }

  getPrices(settings: Settings): Observable<any> {
    const ccmpFavs = `${settings.fav1},${settings.fav2},${settings.fav3},${settings.fav4}`;
    const ccmpApi = `https://min-api.cryptocompare.com/data/pricemulti?fsyms=${ccmpFavs}&tsyms=${settings.currency}`;
    const tickerApi = settings.ticker === 'CCMP' ? ccmpApi : 'https://api.bitpanda.com/v1/ticker';
    return this.http.get<any>(tickerApi).pipe(map(response => {
      const prices = {
        FAV1: this.createPrice(response, settings.fav1, settings.currency),
        FAV2: this.createPrice(response, settings.fav2, settings.currency),
        FAV3: this.createPrice(response, settings.fav3, settings.currency),
        FAV4: this.createPrice(response, settings.fav4, settings.currency),
      };

      this.assets.forEach(asset => {
        prices[asset] = this.createPrice(response, asset, settings.currency);
      });

      this.assetPrices = response;
      return prices;
    }));
  }

  createPrice(res: any, coin: string, currency: string): CoinPrice {
    let trend = Constants.TREND_NORMAL;
    const value = res[coin][currency];
    const locale = this.locale(currency);
    if (this.assetPrices) {
      trend = value > this.assetPrices[coin][currency] ? Constants.TREND_UP :
        (value < this.assetPrices[coin][currency] ? Constants.TREND_DOWN : trend);
    }
    return { coin, value, trend, currency, locale } as CoinPrice;
  }

  locale(currency: string): string {
    return currency === 'USD' ? '' : 'de-DE';
  }

  getAssets(): Observable<string[]> {
    return this.http.get<string[]>(environment.api + 'asset');
  }
}
