package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaWalletsTransactionsData<T> {

    /**
     * id of transaction
     * <p>
     * api response: {@code id}
     * <p>
     * example: 648de980-35ee-11e9-a458-8b689f4be784
     */
    private String id;

    private T attributes;
}
