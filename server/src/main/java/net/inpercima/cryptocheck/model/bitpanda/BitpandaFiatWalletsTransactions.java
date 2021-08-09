package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaFiatWalletsTransactions {

     private BitpandaFiatWalletsTransactionsData[] data;

     private Object meta;

     private Object links;
}
