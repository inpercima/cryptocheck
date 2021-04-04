import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { environment } from '../../environments/environment';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ExternalImportComponent } from './external-import/external-import.component';
import { SettingsComponent } from './settings/settings.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { TradeManagerComponent } from './trade-manager/trade-manager.component';

const routes: Routes = [{
  component: DashboardComponent,
  path: environment.defaultRoute,
}, {
  component: StatisticsComponent,
  path: 'statistics',
}, {
  component: TradeManagerComponent,
  path: 'trade manager',
}, {
  component: ExternalImportComponent,
  path: 'external import',
}, {
  component: SettingsComponent,
  path: 'settings',
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
