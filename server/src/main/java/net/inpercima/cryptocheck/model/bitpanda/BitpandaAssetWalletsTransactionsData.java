package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaAssetWalletsTransactionsData {

    private String id;

    private String type;

    private BitpandaAssetWalletsTransactionsDataAttributes attributes;
}
