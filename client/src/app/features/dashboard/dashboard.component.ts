import { CurrencyPipe } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';

import { Subscription, timer } from 'rxjs';

import { Settings } from '../settings/settings.model';
import { SettingsService } from '../settings/settings.service';
import { DashboardService } from './dashboard.service';
import { AssetService } from 'src/app/core/asset.service';

@Component({
  selector: 'cc-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  fiatWallet: any;

  cryptoWallets: any[];

  prices: any;

  settings: Settings;

  subscription: Subscription;

  profitLossSales = 0;
  profitLossSalesFee = 0;
  profitLossWallets = 0;

  curv = 0;

  constructor(public assetService: AssetService, private dashboardService: DashboardService, private settingsService: SettingsService) { }

  ngOnInit(): void {
    this.assetService.getAssets().subscribe();
    this.settingsService.get().subscribe(settings => {
      this.settings = settings;
      const interval = settings.ticker === 'CCMP' ? 2500 : 10000;
      this.subscription = timer(0, interval).subscribe(() => this.updatePrices());
    });
    this.dashboardService.getFiat().subscribe(fiatWallet => this.fiatWallet = fiatWallet);
    this.dashboardService.getCrypto().subscribe(cryptoWallets => this.cryptoWallets = cryptoWallets);
    this.dashboardService.getProfitLoss().subscribe(profitLoss => {
      this.profitLossSales = profitLoss.sales;
      this.profitLossSalesFee = profitLoss.salesMinusFee;
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  updatePrices(): void {
    this.assetService.getPrices(this.settings).subscribe(prices => {
      this.prices = prices;
      this.profitLossWallets = 0;
      this.cryptoWallets.forEach(value => {
        this.profitLossWallets += this.calculateCurrentProfitLoss(value);
      });
    });
  }

  totalInvestment(): number {
    return this.fiatWallet.investment.internal + this.fiatWallet.investment.external;
  }

  getInvestment(crypto: any): number {
    return crypto.external ? crypto.external : crypto.fiat;
  }

  transformCurrency(value: number): string {
    return new CurrencyPipe(this.assetService.locale(this.settings.currency)).transform(value, this.settings.currency, 'symbol');
  }

  calculateCurrentValue(crypto: any): number {
    return this.prices[crypto.name].value * crypto.coins;
  }

  calculateCurrentProfitLoss(crypto: any): number {
    return this.calculateCurrentValue(crypto) - this.getInvestment(crypto);
  }
}
