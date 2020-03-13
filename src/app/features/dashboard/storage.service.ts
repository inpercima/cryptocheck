import { Injectable } from '@angular/core';
import { CryptoItem } from '../crypto-item/crypto-item.model';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  save(cryptoItems: CryptoItem[]) {
    localStorage.setItem('cryptoItems', JSON.stringify(cryptoItems));
  }

  read(): CryptoItem[] {
    const items = JSON.parse(localStorage.getItem('cryptoItems'));
    return items ? items : [];
  }

  remove() {
    localStorage.removeItem('cryptoItems');
  }
}
