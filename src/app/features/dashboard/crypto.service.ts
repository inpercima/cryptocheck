import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class CryptoService {

  constructor(private http: HttpClient) { }

  public price(): Observable<any> {
    return this.http.get<any>('https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR').pipe(map(response => {
      return response;
    }));
  }
}
