import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';

import { DashboardComponent } from './dashboard/dashboard.component';
import { CryptoItemComponent } from './crypto-item/crypto-item.component';
import { CryptoPriceComponent } from './crypto-price/crypto-price.component';

@NgModule({
  declarations: [
    DashboardComponent,
    CryptoItemComponent,
    CryptoPriceComponent,
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatDividerModule,
    MatInputModule,
    MatProgressBarModule,
    MatSelectModule,
  ],
  exports: [
    DashboardComponent,
  ]
})
export class FeaturesModule { }
