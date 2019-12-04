import { OverlayModule } from '@angular/cdk/overlay';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtModule } from '@auth0/angular-jwt';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AppRoutingPipe } from './app-routing.pipe';
import { CoreModule } from './core/core.module';
import { FeaturesModule } from './features/features.module';
import { LoginModule } from './login/login.module';
import { NotFoundModule } from './not-found/not-found.module';

export function getToken() {
  return localStorage.getItem('access_token');
}

@NgModule({
  bootstrap: [
    AppComponent,
  ],
  declarations: [
    AppComponent,
    AppRoutingPipe,
  ],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    CoreModule,
    FeaturesModule,
    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: getToken,
        whitelistedDomains: ['localhost'],
      }
    }),
    LoginModule,
    MatTabsModule,
    MatToolbarModule,
    NotFoundModule,
    OverlayModule,
  ],
})
export class AppModule { }
