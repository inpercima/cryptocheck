import { Component, OnInit, OnDestroy } from '@angular/core';

import { Subscription, timer } from 'rxjs';

import { Settings } from '../settings/settings.model';
import { SettingsService } from '../settings/settings.service';
import { AssetService } from 'src/app/core/asset.service';

@Component({
  selector: 'cc-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  prices: any;

  settings!: Settings;

  subscription!: Subscription;

  constructor(private assetService: AssetService, private settingsService: SettingsService) { }

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
    this.assetService.getPrices(this.settings).subscribe(prices => this.prices = prices);
  }
}
