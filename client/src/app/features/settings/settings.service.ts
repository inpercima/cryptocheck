import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

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
    return this.http.get<Settings>(environment.api + 'settings');
  }

  save(settings: any): Observable<boolean> {
    return this.http.post<boolean>(environment.api + 'settings', settings);
  }
}
