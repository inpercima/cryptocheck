import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit, Input } from '@angular/core';

import { timer } from 'rxjs';

import { Constants } from 'src/app/core/constants';
import { CoinPrice } from './coin-price.model';

@Component({
  selector: 'cc-coin-price',
  templateUrl: './coin-price.component.html',
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
export class CoinPriceComponent implements OnInit {

  @Input() price: CoinPrice;
  @Input() trend: string;

  constructor() { }

  ngOnInit(): void {
    timer(0, 2500).subscribe(() => this.trend = this.trend !== Constants.TREND_NORMAL ? Constants.TREND_NORMAL : this.trend);
  }
}
