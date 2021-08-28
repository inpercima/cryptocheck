import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Wallet } from './wallet.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  constructor(private http: HttpClient) { }

  getAssetWallets(): Observable<Wallet[]> {
    return this.http.get<Wallet[]>(environment.api + 'wallets/asset').pipe(map(assetWallets => {
      assetWallets.forEach((wallet, index) => {
        this.getUnrelatedTransactionsByAsset(wallet.symbol).subscribe(transactions => {
          const values = {
            amount: 0,
            balance: 0,
          }
          for (const transaction of transactions) {
            this.calculateAmount(transaction, values);
          }
          wallet.investment = values.amount;
        });
      });
      return assetWallets;
    }));
  }

  private calculateAmount(transaction: any, values: any): void {
    const type = transaction.type;
    switch (type) {
      case 'buy':
        values.balance += transaction.number;
        values.amount += transaction.amount;
        break;
      case 'deposit':
        values.balance += transaction.number;
        values.amount += transaction.amount;
        break;
      case 'sell':
        values.balance -= transaction.number;
        values.amount -= transaction.amount;
        break;
      default:
        break;
    }
    values.balance = Number(values.balance.toFixed(8));
    values.amount = values.balance === 0 ? 0 : values.amount
  }

  getFiatWallets(): Observable<Wallet[]> {
    return this.http.get<Wallet[]>(environment.api + 'wallets/fiat');
  }

  getFiatWalletsInvestment(): Observable<Wallet[]> {
    return this.http.get<Wallet[]>(environment.api + 'fiatwallet.php/investments');
  }

  getUnrelatedTransactions(): Observable<any[]> {
    return this.http.get<any[]>(environment.api + 'wallets/asset/transactions/relations/none');
  }

  getUnrelatedTransactionsByAsset(assetSymbol: string): Observable<any[]> {
    return this.http.get<any[]>(environment.api + `wallets/asset/transactions/relations/none/${assetSymbol}`);
  }

  getProfiLossOnTradesPerMonth(month: number): Observable<any[]> {
    const params = new HttpParams();
    return this.http.get<any[]>(environment.api + 'wallet.php/profitloss/trades/month', {
      params: params.append('month', month.toString())
    });
  }
}
