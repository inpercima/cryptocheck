package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaTradeAttributes {

    /**
     * price of cryptocoin at transaction time
     * <p>
     * api response: {@code price}
     * <p>
     * example: 307.20
     */
    private BigDecimal price;
}
