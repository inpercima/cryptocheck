import { state, style, trigger, transition, animate } from '@angular/animations';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { interval, Subscription } from 'rxjs';

import { CryptoService } from './crypto.service';

@Component({
  selector: 'cc-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit, OnDestroy {

  crypto: any;
  cryptoCopy: any;

  subscription: Subscription;

  constructor(private cryptoService: CryptoService) { }

  ngOnInit(): void {
    this.subscription = interval(1000).subscribe(() => this.update());
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  update() {
    this.cryptoService.price().subscribe(crypto => this.crypto = crypto);
  }

}
