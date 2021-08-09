package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaFiatWalletsTransactionsData {

    private String id;

    private String type;

    private BitpandaFiatWalletsTransactionsDataAttributes attributes;
}
