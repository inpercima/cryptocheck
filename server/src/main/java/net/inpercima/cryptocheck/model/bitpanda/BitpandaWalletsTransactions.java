package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaWalletsTransactions<T> {

     private BitpandaWalletsTransactionsData<T>[] data;

     private Object meta;

     private BitpandaLinks links;
}
