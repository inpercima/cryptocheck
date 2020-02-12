import { Component, OnInit, Input } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { interval } from 'rxjs';

import { CryptoService } from '../dashboard/crypto.service';

@Component({
  selector: 'cc-crypto-price',
  templateUrl: './crypto-price.component.html',
  animations: [
    trigger('trend', [
      state(CryptoService.TREND_UP, style({
        backgroundColor: 'green'
      })),
      state(CryptoService.TREND_NORMAL, style({
        backgroundColor: 'white'
      })),
      state(CryptoService.TREND_DOWN, style({
        backgroundColor: 'red'
      })),
      transition(`* => ${CryptoService.TREND_NORMAL}`, [
        animate('1s')
      ]),
    ]),
  ],
})
export class CryptoPriceComponent implements OnInit {

  @Input()
  price: any;

  @Input()
  trend: string;

  constructor() { }

  ngOnInit() {
    interval(2500).subscribe(() => this.trend = this.trend !== CryptoService.TREND_NORMAL ? CryptoService.TREND_NORMAL : this.trend);
  }

  locale(currency: string) {
    return currency === 'EUR' ? 'de-DE' : '';
  }

}
