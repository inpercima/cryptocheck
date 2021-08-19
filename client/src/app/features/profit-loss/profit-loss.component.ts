import { CurrencyPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';

import { map } from 'rxjs/operators';

import { AssetService } from 'src/app/core/asset.service';

@Component({
  selector: 'cc-profit-loss',
  templateUrl: './profit-loss.component.html',
})
export class ProfitLossComponent implements OnInit {

  month = 0;

  profitLossOnTradesPerMonth!: string;

  months = [
    { id: 0, name: 'January' },
    { id: 1, name: 'February' },
    { id: 2, name: 'March' },
    { id: 3, name: 'April' },
    { id: 4, name: 'May'},
    { id: 5, name: 'June' },
    { id: 6, name: 'July' },
    { id: 7, name: 'August' },
    { id: 8, name: 'September' },
    { id: 9, name: 'October' },
    { id: 10, name: 'November' },
    { id: 11, name: 'December' },
  ];

  constructor(private assetService: AssetService) { }

  ngOnInit(): void {
    this.month = new Date().getMonth();
    this.getProfitLossOnTradesPerMonth();
  }

  getProfitLossOnTradesPerMonth(): void {
    // this.assetService.getProfiLossOnTradesPerMonth(this.month).pipe(
    //   map(response => response.reduce((acc, entry) => acc + entry.total, 0))
    // ).subscribe(response => this.profitLossOnTradesPerMonth = new CurrencyPipe(this.assetService.locale('EUR')).transform(response, 'EUR', 'symbol') ?? '');
  }
}
