import { Component, OnDestroy, OnInit } from '@angular/core';
import { interval, Subscription } from 'rxjs';

import { CryptoService } from './crypto.service';
import { CryptoItem } from '../crypto-item/crypto-item.model';

@Component({
  selector: 'cc-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit, OnDestroy {

  cryptoPrices: any;

  cryptoItems: CryptoItem[] = [];

  subscription: Subscription;

  currency = 'USD';

  constructor(private cryptoService: CryptoService) { }

  ngOnInit(): void {
    this.subscription = interval(2500).subscribe(() => this.updatePrice());
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  updatePrice() {
    this.cryptoService.price().subscribe(cryptoPrices => this.cryptoPrices = cryptoPrices);
  }

  profitLoss(): number {
    return this.cryptoItems.reduce((acc, item) => {
      return acc + this.cryptoService.profitLoss(item, this.currency);
    }, 0);
  }

  addItem() {
    const item = {} as CryptoItem;
    item.id = this.cryptoItems.length;
    item.coin = 'BTC';
    item.amount = 0;
    item.purchasePrice = 0;
    this.cryptoItems.push(item);
  }

  updateItem(item: CryptoItem) {
    this.cryptoItems[item.id] = item;
  }

  removeItem(item: CryptoItem) {
    this.cryptoItems = this.cryptoItems.filter(el => el.id !== item.id);
  }
}
