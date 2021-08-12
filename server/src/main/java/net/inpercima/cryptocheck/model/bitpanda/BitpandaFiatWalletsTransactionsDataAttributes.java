package net.inpercima.cryptocheck.model.bitpanda;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaFiatWalletsTransactionsDataAttributes extends BitpandaWalletsTransactionsDataAttributes {

    /**
     * type of fiat
     * <p>
     * api response: {@code fiat_id}
     * <p>
     * example: EUR = 1
     */
    @JsonProperty("fiat_id")
    private Integer fiatId;
}
