import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CryptoItem } from './crypto-item.model';

@Component({
  selector: 'cc-crypto-item',
  templateUrl: './crypto-item.component.html',
  styleUrls: ['./crypto-item.component.css']
})
export class CryptoItemComponent implements OnInit {

  coin: string;
  amount: number;
  purchasePrice: number;

  @Input() item: CryptoItem;
  @Output() updateItem = new EventEmitter<CryptoItem>();
  @Output() removeItem = new EventEmitter<CryptoItem>();

  constructor() { }

  ngOnInit() {
    this.coin = this.item.coin;
    this.amount = this.item.amount;
    this.purchasePrice = this.item.purchasePrice;
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

}
