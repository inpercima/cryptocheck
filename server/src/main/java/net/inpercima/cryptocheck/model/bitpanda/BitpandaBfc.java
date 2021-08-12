package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaBfc {

    /**
     * more attribute information of best fee collection
     * <p>
     * api response: {@code attributes} as object
     */
    private BitpandaBfcAttributes attributes;
}
