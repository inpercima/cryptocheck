import { Component, OnInit } from '@angular/core';
import { TaxService } from './tax.service';

@Component({
  selector: 'cc-tax',
  templateUrl: './tax.component.html',
})
export class TaxComponent implements OnInit {

  trans: any;

  constructor(private taxService: TaxService) { }

  ngOnInit(): void {
    this.taxService.getTransactions().subscribe(trans => this.trans = trans);
  }
}
