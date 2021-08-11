package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaWalletsTransactionsDataAttributes {

    private BigDecimal amount;

    private BigDecimal fee;

    private String type;

    private String status;

    private BitpandaTime time;
}
