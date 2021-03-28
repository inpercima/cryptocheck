import { Component, OnInit, OnDestroy } from '@angular/core';

import { Subscription, timer } from 'rxjs';

import { Settings } from '../settings/settings.model';
import { SettingsService } from '../settings/settings.service';
import { CoinService } from 'src/app/core/coin.service';

@Component({
  selector: 'cc-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  prices: any;

  settings!: Settings;

  subscription!: Subscription;

  constructor(private coinService: CoinService, private settingsService: SettingsService) { }

  ngOnInit(): void {
    this.settingsService.get().subscribe(settings => {
      this.settings = settings;
      const intervalValue = settings.ticker === 'CCMP' ? 2500 : 10000;
      this.subscription = timer(0, intervalValue).subscribe(() => this.updatePrices());
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  updatePrices(): void {
    this.coinService.getPrices(this.settings).subscribe(prices => this.prices = prices);
  }
}
