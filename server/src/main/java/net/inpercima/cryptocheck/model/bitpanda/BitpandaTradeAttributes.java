package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaTradeAttributes {

    /**
     * type of fiat
     * <p>
     * api response: {@code fiat_id}
     * <p>
     * example: EUR = 1
     */
    @JsonProperty("fiat_id")
    private Integer fiatId;

    /**
     * price in used fiat currency of traded asset
     * <p>
     * api response: {@code price}
     * <p>
     * example: 0.08330000
     */
    private BigDecimal price;

    /**
     * exchange rate to euro for used fiat currency of traded asset
     * <p>
     * api response: {@code fiat_to_eur_rate}
     * <p>
     * example: 0.91298687
     */
    @JsonProperty("fiat_to_eur_rate")
    private BigDecimal fiatToEurRate;
}
