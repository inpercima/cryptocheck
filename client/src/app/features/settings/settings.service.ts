import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Settings } from './settings.model';
import { environment } from 'src/environments/environment';

interface Item {

  key: string;

  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  coins: string[] = ['ADA', 'ATOM', 'BAT', 'BEST', 'BCH', 'BCI5', 'BCI10', 'BCI25', 'BTC', 'CHZ', 'COMP', 'DASH', 'DOGE', 'DOT', 'ETC', 'ETH',
    'EOS', 'KMD', 'LINK', 'LSK', 'LTC', 'MIOTA', 'MKR', 'NEO', 'OMG', 'ONT', 'PAN', 'QTUM', 'REP', 'SNX', 'TRX', 'USDC', 'USDT', 'UNI', 'VET',
    'WAVES', 'XAG', 'XAU', 'XEM', 'XLM', 'XPD', 'XPT', 'XRP', 'XTZ', 'YFI', 'ZEC', 'ZRX'];

  currencies: Item[] = [
    { key: 'USD', name: '$ USD' },
    { key: 'EUR', name: 'EUR €' },
  ];

  tickers: Item[] = [
    { key: 'CCMP', name: 'Cryptocompare' },
    { key: 'BPDA', name: 'Bitpanda' },
  ];

  constructor(private http: HttpClient) { }

  get(): Observable<Settings> {
    return this.http.get<Settings>(environment.api + 'settings').pipe(map(response => response));
  }

  save(settings: any): Observable<boolean> {
    return this.http.post<boolean>(environment.api + 'settings', settings).pipe(map(response => response));
  }
}
