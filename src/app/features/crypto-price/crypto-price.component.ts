import { Component, OnInit, Input } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { interval } from 'rxjs';

@Component({
  selector: 'cc-crypto-price',
  templateUrl: './crypto-price.component.html',
  animations: [
    trigger('trend', [
      state('up', style({
        backgroundColor: 'green'
      })),
      state('normal', style({
        backgroundColor: 'white'
      })),
      state('down', style({
        backgroundColor: 'red'
      })),
      transition('* => normal', [
        animate('1s')
      ]),
    ]),
  ],
})
export class CryptoPriceComponent implements OnInit {

  @Input()
  price: number;

  @Input()
  trend: string;

  constructor() { }

  ngOnInit() {
    interval(2500).subscribe(() => {
      this.trend = this.trend !== 'normal' ? 'normal' : this.trend;
    });
  }

  locale(currency: string) {
    return currency === 'EUR' ? 'de-DE' : '';
  }

}
