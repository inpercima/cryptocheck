import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Setting } from '../features/setting/setting.model';
import { AssetPrice } from '../features/dashboard/asset-price/asset-price.model';
import { Constants } from './constants';
import { environment } from 'src/environments/environment';
import { Asset } from './asset.model';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  assetPrices: any;

  assets: Asset[] = [];

  constructor(private http: HttpClient) {
    this.getAssets().subscribe(assets => this.assets = assets);
  }

  getAssets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(environment.api + 'assets').pipe(
      map(asset => asset.sort((a, b) => a.name.localeCompare(b.name)))
    );
  }

  getPrices(setting: Setting): Observable<any> {
    const ccmpFavs = `${setting.fav1},${setting.fav2},${setting.fav3},${setting.fav4}`;
    const ccmpApi = `https://min-api.cryptocompare.com/data/pricemulti?fsyms=${ccmpFavs}&tsyms=${setting.currency}`;
    const tickerApi = setting.ticker === 'CCMP' ? ccmpApi : 'https://api.bitpanda.com/v1/ticker';
    return this.http.get<any>(tickerApi).pipe(map(assets => {
      const prices: { [key: string]: any } = {
        FAV1: this.createPrice(assets, setting.fav1, setting.currency),
        FAV2: this.createPrice(assets, setting.fav2, setting.currency),
        FAV3: this.createPrice(assets, setting.fav3, setting.currency),
        FAV4: this.createPrice(assets, setting.fav4, setting.currency),
      };

      this.assets.forEach(asset => {
        prices[asset.name] = this.createPrice(assets, asset.name, setting.currency);
      });

      this.assetPrices = assets;
      return prices;
    }));
  }

  createPrice(assets: any, asset: string, currency: string): AssetPrice {
    let trend = Constants.TREND_NORMAL;
    const value = assets[asset][currency];
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
}
