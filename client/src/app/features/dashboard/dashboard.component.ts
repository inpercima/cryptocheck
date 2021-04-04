import { Component, OnInit, OnDestroy } from '@angular/core';

import { Subscription, timer } from 'rxjs';

import { Settings } from '../settings/settings.model';
import { SettingsService } from '../settings/settings.service';
import { AssetService } from 'src/app/core/asset.service';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'cc-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  fiatWallets!: any[];
  cryptoWallets!: any[];

  prices: any;

  settings!: Settings;

  subscription!: Subscription;

  constructor(public assetService: AssetService, private settingsService: SettingsService) { }

  ngOnInit(): void {
    this.settingsService.get().subscribe(settings => {
      this.settings = settings;
      const intervalValue = settings.ticker === 'CCMP' ? 2500 : 10000;
      this.subscription = timer(0, intervalValue).subscribe(() => this.updatePrices());
      this.assetService.getFiatWallets().subscribe(fiatWallets => {
        this.assetService.getFiatInvestment().subscribe(fiatInvestments => {
          fiatWallets.forEach((value, index) => {
            fiatWallets[index].internal = fiatInvestments[value.id].internal;
            fiatWallets[index].external = fiatInvestments[value.id].external;
          });
          this.fiatWallets = fiatWallets;
        });
      });
      this.assetService.getCryptoWallets().subscribe(cryptoWallets => {
        this.assetService.getCryptoInvestment(cryptoWallets.map(elem => elem.id)).subscribe(cryptoInvestments => {
          cryptoWallets.forEach((value, index) => {
            cryptoWallets[index].internal = cryptoInvestments[value.id].internal;
            cryptoWallets[index].external = cryptoInvestments[value.id].external;
          });
          this.cryptoWallets = cryptoWallets;
        });
      });
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  updatePrices(): void {
    this.assetService.getPrices(this.settings).subscribe(prices => this.prices = prices);
  }

  transformCurrency(value: number): string {
    const currency = this.settings.currency;
    return new CurrencyPipe(this.assetService.locale(currency)).transform(value, currency, 'symbol') ?? '';
  }

  calculateCurrentValue(asset: any): number {
    return this.prices[asset.symbol].value * asset.balance;
  }

  determineInvestment(asset: any): number {
    return asset.internal + asset.external;
  }

  calculateCurrentProfitLoss(asset: any): string {
    return this.transformCurrency(this.calculateCurrentValue(asset) - asset.internal - asset.external);
  }
}
