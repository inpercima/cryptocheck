import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AssetPriceComponent } from './asset-price/asset-price.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ExternalImportComponent } from './external-import/external-import.component';
import { FeaturesRoutingModule } from './features-routing.module';
import { MaterialModule } from '../shared/material/material.module';
import { ProfitLossComponent } from './profit-loss/profit-loss.component';
import { SettingsComponent } from './settings/settings.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { TradeManagerComponent } from './trade-manager/trade-manager.component';

@NgModule({
  declarations: [
    AssetPriceComponent,
    DashboardComponent,
    ExternalImportComponent,
    ProfitLossComponent,
    SettingsComponent,
    StatisticsComponent,
    TradeManagerComponent,
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
