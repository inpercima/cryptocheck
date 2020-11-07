import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  getFiat(): Observable<any> {
    return this.http.get<any>(environment.api + 'transaction?type=fiat').pipe(map(response => response));
  }

  getCrypto(): Observable<any> {
    return this.http.get<any>(environment.api + 'transaction?type=crypto').pipe(map(response => response));
  }
}
