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
        this.getUnrelatedTransactions(wallet.symbol).subscribe(transactions => {
          let amount = 0;
          let balance = 0;
          for (const transaction of transactions) {
            if (balance >= wallet.balance) {
              break;
            }
            amount = transaction.type === 'buy' ? amount + transaction.amount : amount - transaction.amount;
            balance = wallet.balance;
          }
          wallet.investment = amount;
        });
      });
      return assetWallets;
    }));
  }

  getFiatWallets(): Observable<Wallet[]> {
    return this.http.get<Wallet[]>(environment.api + 'wallets/fiat');
  }

  getFiatWalletsInvestment(): Observable<Wallet[]> {
    return this.http.get<Wallet[]>(environment.api + 'fiatwallet.php/investments');
  }

  getUnrelatedTransactions(assetSymbol: string): Observable<any[]> {
    const params = new HttpParams();
    return this.http.get<any[]>(environment.api + 'wallets/asset/transactions/relations/none', {
      params: params.append('assetSymbol', assetSymbol),
    });
  }

  getProfiLossOnTradesPerMonth(month: number): Observable<any[]> {
    const params = new HttpParams();
    return this.http.get<any[]>(environment.api + 'wallet.php/profitloss/trades/month', {
      params: params.append('month', month.toString())
    });
  }
}
