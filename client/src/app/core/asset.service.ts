import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Settings } from '../features/settings/settings.model';
import { AssetPrice } from '../features/asset-price/asset-price.model';
import { Asset } from './asset.model';
import { Constants } from './constants';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  assets: string[] = [];

  assetPrice: any;

  constructor(private http: HttpClient) {
    this.getAssets().subscribe(response => this.assets = response);
   }

  getAssets(): Observable<string[]> {
    return this.http.get<string[]>(environment.api + 'asset');
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
      this.assetPrice = response;
      return prices;
    }));
  }

  createPrice(res: any, asset: string, currency: string): AssetPrice {
    let trend = Constants.TREND_NORMAL;
    const value = res[asset][currency];
    const locale = this.locale(currency);
    if (this.assetPrice) {
      trend = value > this.assetPrice[asset][currency] ? Constants.TREND_UP :
        (value < this.assetPrice[asset][currency] ? Constants.TREND_DOWN : trend);
    }
    return { asset, value, trend, currency, locale } as AssetPrice;
  }

  locale(currency: string): string {
    return currency === 'USD' ? '' : 'de-DE';
  }

  getFiatWallets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(environment.api + 'fiatwallet.php/used').pipe(map(response => response));
  }

  getCryptoWallets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(environment.api + 'wallet.php/used').pipe(map(response => response));
  }
}
