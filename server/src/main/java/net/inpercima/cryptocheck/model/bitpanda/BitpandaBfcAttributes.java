package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaBfcAttributes {

    /**
     * amount in euro for best fee collection
     * <p>
     * api response: {@code bfc_market_value_eur}
     * <p>
     * example: 6.40327769
     */
    @JsonProperty("bfc_market_value_eur")
    private BigDecimal bfcMarketValueEur;

    /**
     * price in euro for best fee collection
     * <p>
     * api response: {@code best_current_price_eur}
     * <p>
     * example: 0.08024207
     */
    @JsonProperty("best_current_price_eur")
    private BigDecimal bestCurrentPriceEur;

    /**
     * more trade information of best fee collection
     * <p>
     * api response: {@code related_trade} as object
     */
    @JsonProperty("related_trade")
    public BitpandaTrade relatedTrade;
}
