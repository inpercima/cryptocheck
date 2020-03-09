import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { CryptoItem } from '../crypto-item/crypto-item.model';
import { CryptoPrice } from '../crypto-price/crypto-price.model';

import { Constants } from '../../core/constants';

@Injectable({
  providedIn: 'root'
})
export class CryptoService {

  cryptoPrices: any;
  prices: any;

  constructor(private http: HttpClient) { }

  price(): Observable<any> {
    return this.http.get<any>('https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR').pipe(map(response => {
      const prices = {
        BTCUSD: this.createPrice(response, 'BTC', 'USD', ''),
        BTCEUR: this.createPrice(response, 'BTC', 'EUR', 'de-DE'),
        ETHUSD: this.createPrice(response, 'ETH', 'USD', ''),
        ETHEUR: this.createPrice(response, 'ETH', 'EUR', 'de-DE'),
      };
      this.cryptoPrices = response;
      this.prices = prices;
      return prices;
    }));
  }

  createPrice(res: any, coin: string, currency: string, locale: string): CryptoPrice {
    let trend = Constants.TREND_NORMAL;
    const value = res[coin][currency];
    if (this.cryptoPrices) {
      trend = value > this.cryptoPrices[coin][currency] ? Constants.TREND_UP :
        (value < this.cryptoPrices[coin][currency] ? Constants.TREND_DOWN : trend);
    }
    return { coin, currency, value, trend, locale } as CryptoPrice;
  }

  profitLoss(item: CryptoItem, currency: string): number {
    return (this.prices[item.coin + currency].value * item.amount) - (item.amount * item.purchasePrice);
  }

  locale(currency: string) {
    return currency === 'USD' ? '' : 'de-DE';
  }
}
