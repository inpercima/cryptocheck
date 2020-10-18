import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { CoinPrice } from '../features/coin-price/coin-price.model';
import { Settings } from '../features/settings/settings.model';
import { Constants } from './constants';

@Injectable({
  providedIn: 'root'
})
export class CoinService {

  coinPrices: any;

  constructor(private http: HttpClient) { }

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
      this.coinPrices = response;
      return prices;
    }));
  }

  createPrice(res: any, coin: string, currency: string): CoinPrice {
    let trend = Constants.TREND_NORMAL;
    const value = res[coin][currency];
    const locale = this.locale(currency);
    if (this.coinPrices) {
      trend = value > this.coinPrices[coin][currency] ? Constants.TREND_UP :
        (value < this.coinPrices[coin][currency] ? Constants.TREND_DOWN : trend);
    }
    return { coin, value, trend, currency, locale } as CoinPrice;
  }

  locale(currency: string): string {
    return currency === 'USD' ? '' : 'de-DE';
  }
}
