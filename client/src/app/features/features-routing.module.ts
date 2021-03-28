import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { environment } from '../../environments/environment';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SettingsComponent } from './settings/settings.component';

const routes: Routes = [{
  component: DashboardComponent,
  path: environment.defaultRoute,
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
