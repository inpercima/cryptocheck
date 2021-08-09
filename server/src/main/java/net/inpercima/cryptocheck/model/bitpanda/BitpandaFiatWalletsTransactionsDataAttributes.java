package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaFiatWalletsTransactionsDataAttributes {

    @JsonProperty("fiat_id")
    private Integer fiatId;

    private BigDecimal amount;

    private BigDecimal fee;

    private String type;

    private String status;
}
