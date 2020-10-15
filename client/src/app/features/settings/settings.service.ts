import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from 'src/environments/environment';

interface Ticker {

  key: string;

  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  coins: string[] = ['BTC', 'BCI5', 'BEST', 'PAN', 'BCI10', 'BCI25', 'XAU', 'ETH', 'LINK', 'USDT', 'MIOTA', 'XRP', 'ADA', 'TRX', 'VET',
    'OMG', 'NEO', 'QTUM', 'LTC', 'XEM', 'XTZ', 'XAG', 'YFI', 'CHZ', 'XLM', 'ONT', 'BCH', 'USDC', 'EOS', 'UNI', 'WAVES', 'ATOM', 'DOT',
    'SNX', 'DASH', 'ZRX', 'BAT', 'KMD', 'ETC', 'DOGE', 'ZEC', 'XPD', 'REP', 'LSK', 'COMP', 'XPT', 'MKR'];

  currencies: string[] = ['EUR', 'USD'];

  tickers: Ticker[] = [
    { key: 'CCMP', name: 'Cryptocompare' },
    { key: 'BPDA', name: 'Bitpanda' },
  ];

  constructor(private http: HttpClient) { }

  get(): Observable<any> {
    return this.http.get<any>(environment.api + 'settings').pipe(map(response => response));
  }

  save(settings: any): Observable<any> {
    return this.http.post<any>(environment.api + 'settings', settings).pipe(map(response => response));
  }
}
