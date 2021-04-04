import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SyncDialogComponent } from '../shared/sync-dialog/sync-dialog.component';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SyncDialogService {

  isDialogOpen = false;

  constructor(private dialog: MatDialog) { }

  openDialog(): void {
    if (!this.isDialogOpen) {
      this.isDialogOpen = true;
      const dialogRef = this.dialog.open(SyncDialogComponent, {
        width: '300px',
        panelClass: `${environment.theme}-theme`
      });
      dialogRef.afterClosed().subscribe(() => this.isDialogOpen = false);
    }
  }
}
