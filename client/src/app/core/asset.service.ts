import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Setting } from '../features/setting/setting.model';
import { AssetPrice } from '../features/asset-price/asset-price.model';
import { Asset } from './asset.model';
import { Constants } from './constants';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  assetPrices: any;

  assets: Asset[] = [];

  constructor(private http: HttpClient) {
    this.getAssets().subscribe(response => this.assets = response);
  }

  getAssets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(environment.api + 'assets');
  }

  getPrices(setting: Setting): Observable<any> {
    const ccmpFavs = `${setting.fav1},${setting.fav2},${setting.fav3},${setting.fav4}`;
    const ccmpApi = `https://min-api.cryptocompare.com/data/pricemulti?fsyms=${ccmpFavs}&tsyms=${setting.currency}`;
    const tickerApi = setting.ticker === 'CCMP' ? ccmpApi : 'https://api.bitpanda.com/v1/ticker';
    return this.http.get<any>(tickerApi).pipe(map(response => {
      const prices: { [key: string]: any } = {
        FAV1: this.createPrice(response, setting.fav1, setting.currency),
        FAV2: this.createPrice(response, setting.fav2, setting.currency),
        FAV3: this.createPrice(response, setting.fav3, setting.currency),
        FAV4: this.createPrice(response, setting.fav4, setting.currency),
      };

      this.assets.forEach(asset => {
        prices[asset.name] = this.createPrice(response, asset.name, setting.currency);
      });

      this.assetPrices = response;
      return prices;
    }));
  }

  createPrice(res: any, asset: string, currency: string): AssetPrice {
    let trend = Constants.TREND_NORMAL;
    const value = res[asset][currency];
    const locale = this.locale(currency);
    if (this.assetPrices) {
      trend = value > this.assetPrices[asset][currency] ? Constants.TREND_UP :
        (value < this.assetPrices[asset][currency] ? Constants.TREND_DOWN : trend);
    }
    return { asset, value, trend, currency, locale } as AssetPrice;
  }

  locale(currency: string): string {
    return currency === 'USD' ? '' : 'de-DE';
  }

  getFiatWallets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(environment.api + 'wallets/fiat');
  }

  getCryptoWallets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(environment.api + 'wallets/asset');
  }

  getFiatInvestment(): Observable<any[]> {
    return this.http.get<any[]>(environment.api + 'fiatwallet.php/investments');
  }

  getCryptoInvestment(cryptoIds: number[]): Observable<any[]> {
    const params = new HttpParams();
    return this.http.get<any[]>(environment.api + 'wallet.php/investments', { params: params.append('cryptoIds', cryptoIds.join(', ')) });
  }

  getUncheckedTransactions(): Observable<any[]> {
    return this.http.get<any[]>(environment.api + 'wallet.php/transactions/unchecked');
  }

  getProfiLossOnTradesPerMonth(month: number): Observable<any[]> {
    const params = new HttpParams();
    return this.http.get<any[]>(environment.api + 'wallet.php/profitloss/trades/month', {
      params: params.append('month', month.toString())
    });
  }
}
