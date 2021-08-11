package net.inpercima.cryptocheck.model.bitpanda;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaFiatWalletsTransactionsDataAttributes extends BitpandaWalletsTransactionsDataAttributes {

    @JsonProperty("fiat_id")
    private Integer fiatId;
}
