package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaAssetWalletsTransactions {

     private BitpandaAssetWalletsTransactionsData[] data;

     private Object meta;

     private BitpandaLinks links;
}
