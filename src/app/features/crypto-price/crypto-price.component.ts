import { Component, OnInit, Input } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';

import { interval } from 'rxjs';

import { CryptoPrice } from './crypto-price.model';
import { Constants } from 'src/app/core/constants';

@Component({
  selector: 'cc-crypto-price',
  templateUrl: './crypto-price.component.html',
  animations: [
    trigger('trend', [
      state(Constants.TREND_UP, style({
        backgroundColor: 'green'
      })),
      state(Constants.TREND_NORMAL, style({
        backgroundColor: 'white'
      })),
      state(Constants.TREND_DOWN, style({
        backgroundColor: 'red'
      })),
      transition(`* => ${Constants.TREND_NORMAL}`, [
        animate('1s')
      ]),
    ]),
  ],
})
export class CryptoPriceComponent implements OnInit {

  @Input() price: CryptoPrice;
  @Input() trend: string;

  constructor() { }

  ngOnInit() {
    interval(2500).subscribe(() => this.trend = this.trend !== Constants.TREND_NORMAL ? Constants.TREND_NORMAL : this.trend);
  }

}
