package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaAssetWalletsTransactionsDataAttributes extends BitpandaWalletsTransactionsDataAttributes {

    /**
     * type of asset
     * <p>
     * api response: {@code cryptocoin_id}
     * <p>
     * example: BTC = 1, ETH = 5
     */
    @JsonProperty("cryptocoin_id")
    private Integer cryptocoinId;

    /**
     * amount in euro
     * <p>
     * api response: {@code amount_eur}
     * <p>
     * example: 5000.00
     */
    @JsonProperty("amount_eur")
    private BigDecimal amountEur;

    /**
     * more trade information of transaction
     * <p>
     * api response: {@code trade} as object
     */
    private BitpandaTrade trade;
}
