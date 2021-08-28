import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TaxService {

  sum: number = 0;

  constructor(private http: HttpClient) { }

  getTransactions(): Observable<any[]> {
    const year = 2020;
    return this.http.get<any[]>(environment.api + `wallets/asset/transactions/relations/${year}`).pipe(map(relationContainers => {
      const overview: any[] = [];
      relationContainers.forEach((relatedTransactions: any[]) => {
        const sells = relatedTransactions.filter(t => t.type === 'sell');
        if (sells.length) {
          relatedTransactions.filter(t => t.type === 'buy').forEach((transaction: any) => {
            transaction.holdingPeriod = this.calculateHoldingPeriod(transaction.date, sells);
            transaction.profitLoss = this.calculateProfitLoss(transaction.number, transaction.amount, sells);
            overview.push(transaction);
          });
        }

      });
      this.sum = overview.filter(e => e.holdingPeriod !== null).map(e => e.profitLoss)
        .reduce((accumulator, currentValue) => accumulator + currentValue, 0);
      console.log(this.sum);
      return overview;
    }));
  }

  calculateProfitLoss(number: number, amount: number, sells: any[]) {
    let sellsAmount = 0;
    let sellsNumber = 0;
    if (sells.length === 1) {
      sellsAmount = sells[0].amount;
      sellsNumber = sells[0].number;
      return this.calculateProfitLossSingle(number, amount, sellsAmount, sellsNumber);
    } else {
      // multiple sells, currently not used
      return 0;
    }
  }

  calculateProfitLossSingle(number: number, amount: number, sellsAmount: number, sellsNumber: number) {
    return (number * sellsAmount / sellsNumber) - amount;
  }

  calculateHoldingPeriod(startDate: string, sells: any[]) {
    let endDate = null;
    if (sells.length === 1) {
      endDate = sells[0].date;
    } else {
      // multiple sells, currently not used
    }
    const time = Date.parse(endDate.substr(0, 10)) - Date.parse(startDate.substr(0, 10));
    // difference in days
    return time / (1000 * 3600 * 24);
  }
}
