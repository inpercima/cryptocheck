import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';

import { CryptoService } from '../dashboard/crypto.service';
import { CryptoItem } from './crypto-item.model';
import { Subscription, interval } from 'rxjs';

@Component({
  selector: 'cc-crypto-item',
  templateUrl: './crypto-item.component.html',
  styleUrls: ['./crypto-item.component.css'],
})
export class CryptoItemComponent implements OnInit, OnDestroy {

  cryptoPrices: any;

  subscription: Subscription;

  coin: string;
  amount: number;
  purchasePrice: number;

  @Input() currency;
  @Input() item: CryptoItem;

  @Output() updateItem = new EventEmitter<CryptoItem>();
  @Output() removeItem = new EventEmitter<CryptoItem>();

  constructor(private cryptoService: CryptoService) { }

  ngOnInit() {
    this.coin = this.item.coin;
    this.amount = this.item.amount;
    this.purchasePrice = this.item.purchasePrice;
    this.subscription = interval(100).subscribe(() => this.updatePrice());
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  updatePrice() {
    this.cryptoService.price().subscribe(cryptoPrices => this.cryptoPrices = cryptoPrices);
  }

  changeItem(): void {
    this.item.coin = this.coin;
    this.item.amount = this.amount;
    this.item.purchasePrice = this.purchasePrice;
    this.updateItem.emit(this.item);
  }

  remove() {
    this.removeItem.emit(this.item);
  }

  profitLoss() {
    return this.cryptoService.profitLoss(this.item, this.currency);
  }

  conditionalColor() {
    return this.profitLoss() < 0 ? { 'color': 'red' } : this.profitLoss() > 0 ? { 'color': 'green' } : { 'color': 'black' };
  }

}
