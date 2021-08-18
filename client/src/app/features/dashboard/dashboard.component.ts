import { Component, OnInit, OnDestroy } from '@angular/core';

import { Subscription, timer } from 'rxjs';

import { Setting } from '../setting/setting.model';
import { SettingService } from '../setting/setting.service';
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

  setting!: Setting;

  subscription!: Subscription;

  constructor(public assetService: AssetService, private settingService: SettingService) { }

  ngOnInit(): void {
    this.settingService.get().subscribe(setting => {
      this.setting = setting;
      if (setting && setting.id !== null) {
        const intervalValue = setting.ticker === 'CCMP' ? 2500 : 10000;
        this.subscription = timer(0, intervalValue).subscribe(() => this.updatePrices());
        this.assetService.getFiatWallets().subscribe(fiatWallets => {
          // this.assetService.getFiatInvestment().subscribe(fiatInvestments => {
          //   fiatWallets.forEach((value, index) => {
          //     fiatWallets[index].internal = fiatInvestments[value.id].internal;
          //     fiatWallets[index].external = fiatInvestments[value.id].external;
          //   });
          this.fiatWallets = fiatWallets;
        });
        // });
        this.assetService.getAssetWallets().subscribe(cryptoWallets => this.cryptoWallets = cryptoWallets);
      }
    });
  }

  ngOnDestroy(): void {
    // may be undefined if no settings on load will be found so the check with ? is need
    this.subscription?.unsubscribe();
  }

  updatePrices(): void {
    this.assetService.getPrices(this.setting).subscribe(prices => this.prices = prices);
  }

  transformCurrency(value: number): string {
    const currency = this.setting.currency;
    return new CurrencyPipe(this.assetService.locale(currency)).transform(value, currency, 'symbol') ?? '';
  }

  calculateCurrentValue(asset: any): number {
    return this.prices[asset.symbol].value * asset.balance;
  }

  determineInvestment(asset: any): number {
    // return asset.internal + asset.external;
    return 0;

  }

  calculateCurrentProfitLoss(asset: any): string {
    return this.transformCurrency(this.calculateCurrentValue(asset) - asset.investment);
  }
}
