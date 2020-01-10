import { state, style, trigger, transition, animate } from '@angular/animations';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { interval, Subscription } from 'rxjs';

import { CryptoService } from './crypto.service';

@Component({
  selector: 'cc-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  animations: [
    trigger('trendUp', [
      state('color', style({
        backgroundColor: 'green'
      })),
      state('white', style({
        backgroundColor: 'white'
      })),
      transition('color => white', [
        animate('0.3s')
      ]),
    ]),
    trigger('trendDown', [
      state('color', style({
        backgroundColor: 'red'
      })),
      state('white', style({
        backgroundColor: 'white'
      })),
      transition('color => white', [
        animate('0.8s')
      ]),
    ]),
  ],
})
export class DashboardComponent implements OnInit, OnDestroy {

  crypto: any;
  cryptoCopy: any;

  subscription: Subscription;

  constructor(private cryptoService: CryptoService) { }

  ngOnInit(): void {
    this.subscription = interval(1000).subscribe(val => this.update());
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  update() {
    this.cryptoService.price().subscribe(crypto => this.crypto = crypto);
  }

  checkTrend(trendUp: boolean, coin: string, currency: string): string {
    let result = 'white';
    if (this.cryptoCopy) {
      if (trendUp) {
        result = this.crypto[coin][currency] > this.cryptoCopy[coin][currency] ? 'color' : 'white';
      } else {
        result = this.crypto[coin][currency] < this.cryptoCopy[coin][currency] ? 'color' : 'white';
      }
    }
    this.cryptoCopy = this.crypto;
    return result;
  }

}
