import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';

import { FeaturesRoutingModule } from './features-routing.module';
import { AssetPriceComponent } from './asset-price/asset-price.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SettingsComponent } from './settings/settings.component';
import { MaterialModule } from '../shared/material/material.module';
import { StatisticsComponent } from './statistics/statistics.component';
import { TradeManagerComponent } from './trade-manager/trade-manager.component';
import { ExternalImportComponent } from './external-import/external-import.component';

@NgModule({
  declarations: [
    AssetPriceComponent,
    DashboardComponent,
    SettingsComponent,
    StatisticsComponent,
    TradeManagerComponent,
    ExternalImportComponent,
  ],
  imports: [
    CommonModule,
    FeaturesRoutingModule,
    FlexLayoutModule,
    HttpClientModule,
    MaterialModule,
    ReactiveFormsModule,
  ]
})
export class FeaturesModule { }
