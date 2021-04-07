import { Component, OnInit } from '@angular/core';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';

import { AssetService } from 'src/app/core/asset.service';

@Component({
  selector: 'cc-trade-manager',
  templateUrl: './trade-manager.component.html',
})
export class TradeManagerComponent implements OnInit {

  buySells: any[] = [];
  matches: any[] = [];

  type: string[] = [];
  asset: string[] = [];
  number = 0;

  constructor(private assetService: AssetService) { }

  ngOnInit(): void {
    this.assetService.getUncheckedTransactions().subscribe(buySells => this.buySells = buySells);
  }

  drop(event: CdkDragDrop<string[]>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
    }
    // https://stackoverflow.com/a/14438954
    this.type = [...new Set(this.matches.map(asset => asset.type))];
    this.asset = [...new Set(this.matches.map(asset => asset.name))];
    this.number = this.matches.map(asset => asset.type === 'sell' ? -asset.number : asset.number).reduce((a, b) => a + b, 0);
  }

  getBackgroundColor(badge: string): string {
    let result = 'red';
    if (badge === 'type') {
      result = this.type.length === 2 ? 'green' : result;
    } else if (badge === 'asset') {
      result = this.asset.length === 1 ? 'green' : result;
    } else {
      result = this.number === 0 ? 'green' : result;
    }
    return result;
  }
}
