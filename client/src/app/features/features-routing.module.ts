import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { environment } from '../../environments/environment';

const routes: Routes = [{
  component: DashboardComponent,
  path: environment.defaultRoute,
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
