import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { Setting } from './setting.model';
import { environment } from 'src/environments/environment';

interface Item {

  key: string;

  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class SettingService {

  currencies: Item[] = [
    { key: 'USD', name: '$ USD' },
    { key: 'EUR', name: 'EUR €' },
  ];

  tickers: Item[] = [
    { key: 'CCMP', name: 'Cryptocompare' },
    { key: 'BPDA', name: 'Bitpanda' },
  ];

  constructor(private http: HttpClient) { }

  get(): Observable<Setting> {
    return this.http.get<Setting>(environment.api + 'settings');
  }

  save(settings: any): Observable<boolean> {
    return this.http.put<boolean>(environment.api + 'settings', settings);
  }
}
