package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaTradeAttributes {

    /**
     * price in euro of traded asset
     * <p>
     * api response: {@code price}
     * <p>
     * example: 0.08330000
     */
    private BigDecimal price;
}
