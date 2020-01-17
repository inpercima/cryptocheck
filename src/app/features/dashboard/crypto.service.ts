import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class CryptoService {

  public static TREND_NORMAL = 'normal';

  public static TREND_UP = 'up';

  public static TREND_DOWN = 'down';

  cryptoPrices: any;

  constructor(private http: HttpClient) { }

  price(): Observable<any> {
    return this.http.get<any>('https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR').pipe(map(response => {
      const prices = {
        BTCUSD: this.createPrice(response, 'BTC', 'USD'),
        BTCEUR: this.createPrice(response, 'BTC', 'EUR'),
        ETHUSD: this.createPrice(response, 'ETH', 'USD'),
        ETHEUR: this.createPrice(response, 'ETH', 'EUR'),
      };
      this.cryptoPrices = response;
      return prices;
    }));
  }

  createPrice(res: any, coin: string, currency: string): any {
    let trend = CryptoService.TREND_NORMAL;
    const value = res[coin][currency];
    if (this.cryptoPrices) {
      trend = value > this.cryptoPrices[coin][currency] ? CryptoService.TREND_UP :
        (value < this.cryptoPrices[coin][currency] ? CryptoService.TREND_DOWN : trend);
    }
    return { coin, currency, value, trend } as any;
  }


}
