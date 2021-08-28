import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { environment } from '../../environments/environment';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ExternalImportComponent } from './external-import/external-import.component';
import { ProfitLossComponent } from './profit-loss/profit-loss.component';
import { SettingComponent } from './setting/setting.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { TaxComponent } from './tax/tax.component';
import { TradeManagerComponent } from './trade-manager/trade-manager.component';

const routes: Routes = [{
  component: DashboardComponent,
  path: environment.defaultRoute,
}, {
  component: ProfitLossComponent,
  path: 'profit-loss',
}, {
  component: StatisticsComponent,
  path: 'statistics',
}, {
  component: TradeManagerComponent,
  path: 'trade-manager',
}, {
  component: ExternalImportComponent,
  path: 'external-import',
}, {
  component: SettingComponent,
  path: 'settings',
}, {
  component: TaxComponent,
  path: 'tax',
}];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [
    RouterModule,
  ],
})
export class FeaturesRoutingModule {

  static ROUTES = routes;
}
