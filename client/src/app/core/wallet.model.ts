import { Asset } from './asset.model';

export interface Wallet extends Asset {

  balance: number;

  investment: number;
}
