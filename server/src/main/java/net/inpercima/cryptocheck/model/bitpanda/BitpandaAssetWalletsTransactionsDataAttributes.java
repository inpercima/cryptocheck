package net.inpercima.cryptocheck.model.bitpanda;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaAssetWalletsTransactionsDataAttributes extends BitpandaWalletsTransactionsDataAttributes {

    @JsonProperty("cryptocoin_id")
    private Integer cryptocoinId;
}
