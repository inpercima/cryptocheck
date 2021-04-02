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
      this.assetService.getFiatWallets().subscribe(fiatWallets => this.fiatWallets = fiatWallets);
      this.assetService.getCryptoWallets().subscribe(cryptoWallets => this.cryptoWallets = cryptoWallets);
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  updatePrices(): void {
    this.assetService.getPrices(this.settings).subscribe(prices => this.prices = prices);
  }

  // totalInvestment(): number {
  //   return this.fiatWallet.investment.internal + this.fiatWallet.investment.external;
  // }

  getInvestment(crypto: any): number {
    return crypto.external ? crypto.external : crypto.fiat;
  }

  transformCurrency(value: number): string {
    const currency = this.settings.currency;
    return new CurrencyPipe(this.assetService.locale(currency)).transform(value, currency, 'symbol') ?? '';
  }

  calculateCurrentValue(asset: any): number {
    return this.prices[asset.symbol].value * asset.balance;
  }

  calculateCurrentProfitLoss(crypto: any): string {
    return this.transformCurrency(this.calculateCurrentValue(crypto) -  - this.getInvestment(crypto));
  }
}
