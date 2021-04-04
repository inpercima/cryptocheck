import { OverlayModule } from '@angular/cdk/overlay';
import { registerLocaleData } from '@angular/common';
import localeDe from '@angular/common/locales/de';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AppRoutingPipe } from './app-routing.pipe';
import { FeaturesModule } from './features/features.module';
import { MaterialModule } from './shared/material/material.module';
import { SyncDialogComponent } from './shared/sync-dialog/sync-dialog.component';

registerLocaleData(localeDe);

@NgModule({
  declarations: [
    AppComponent,
    AppRoutingPipe,
    SyncDialogComponent,
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    FlexLayoutModule,
    MaterialModule,
    OverlayModule,
    AppRoutingModule,
    FeaturesModule,
  ],
  bootstrap: [
    AppComponent,
  ],
})
export class AppModule { }
