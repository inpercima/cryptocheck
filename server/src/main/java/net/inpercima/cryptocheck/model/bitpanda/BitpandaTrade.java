package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaTrade {

    /**
     * more attribute information of trade
     * <p>
     * api response: {@code attributes} as object
     */
    private BitpandaTradeAttributes attributes;
}
