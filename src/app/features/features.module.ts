import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import { DashboardComponent } from './dashboard/dashboard.component';
import { FeaturesRoutingModule } from './features-routing.module';
import { CryptoService } from './dashboard/crypto.service';

@NgModule({
  declarations: [
    DashboardComponent,
  ],
  imports: [
    CommonModule,
    FeaturesRoutingModule,
    FlexLayoutModule,
    MatCardModule,
    MatProgressBarModule,
  ],
  providers: [
    CryptoService,
  ]
})
export class FeaturesModule { }
