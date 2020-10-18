import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';

import { MaterialModule } from '../shared/material/material.module';
import { FeaturesRoutingModule } from './features-routing.module';
import { CoinPriceComponent } from './coin-price/coin-price.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SettingsComponent } from './settings/settings.component';

@NgModule({
  declarations: [
    CoinPriceComponent,
    DashboardComponent,
    SettingsComponent,
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    MaterialModule,
    FeaturesRoutingModule,
  ]
})
export class FeaturesModule { }
