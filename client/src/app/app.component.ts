import { OverlayContainer } from '@angular/cdk/overlay';
import { Component, HostBinding } from '@angular/core';
import { Routes } from '@angular/router';
import { Title } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { FeaturesRoutingModule } from './features/features-routing.module';
import { environment } from '../environments/environment';
import { SyncDialogService } from './core/sync-dialog.service';

@Component({
  selector: 'cc-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {

  public appname: string;

  public routes: Routes;

  /**
   * Adds the custom theme to the app root.
   * For overlays the OverlayContainer like in the constructor is used.
   * For dialogs the panelClass of the configuration must added manually like
   *
   * const dialogConfig = new MatDialogConfig();
   * dialogConfig.panelClass = `${environment.theme}-theme`;
   */
  @HostBinding('class') class = `${environment.theme}-theme`;

  public constructor(private titleService: Title, public overlayContainer: OverlayContainer, private syncDialogService: SyncDialogService) {
    this.appname = environment.appname;
    this.routes = AppRoutingModule.ROUTES.concat(FeaturesRoutingModule.ROUTES);
    this.titleService.setTitle(this.appname);
    this.overlayContainer.getContainerElement().classList.add(`${environment.theme}-theme`);
  }

  syncAccountData(): void {
    this.syncDialogService.openDialog();
  }
}
