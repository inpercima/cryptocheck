package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaWalletsTransactionsDataAttributes {

    /**
     * api response: {@code amount}
     * <p>
     * <pre>
     * asset: amount of cryptocoins
     * fiat: amount in euro of this transaction
     * </pre>
     * <p>
     * <p>
     * example: 100.00
     */
    private BigDecimal amount;

    /**
     * fee of a transaction
     * <p>
     * api response: {@code fee}
     * <p>
     * example: 2.00
     */
    private BigDecimal fee;

    /**
     * type of transaction
     * <p>
     * api response: {@code type}
     * <p>
     * asset: buy, sell, deposit, withdrawal, transfer, refund, ico
     * <p>
     * fiat: deposit, withdrawal, transfer, refund <br>
     * example: deposit
     */
    private String type;

    /**
     * date of transaction
     * <p>
     * api response: {@code date}
     * <p>
     * example: 2021-06-06 16:01:12
     */
    private String status;

    /**
     * date and time of transaction
     * <p>
     * api response: {@code time} as object
     */
    private BitpandaTime time;
}
