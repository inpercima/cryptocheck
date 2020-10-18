import { Component, OnInit } from '@angular/core';
import { SyncService } from '../../core/sync.service';

@Component({
  selector: 'cc-sync-dialog',
  templateUrl: './sync-dialog.component.html',
  styleUrls: ['./sync-dialog.component.css']
})
export class SyncDialogComponent implements OnInit {

  info: string;

  loading: boolean;

  constructor(private syncService: SyncService) { }

  ngOnInit(): void {
    this.loading = true;
    this.syncService.sync().subscribe(response => {
      this.loading = false;
      const count: number = Number(response) || 0;
      this.info = count.toString();
      this.info += count === 1 ? ' new entry' : ' new entries';
    });
  }
}
