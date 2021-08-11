package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaAssetWalletsTransactionsDataAttributes {

    @JsonProperty("cryptocoin_id")
    private Integer cryptocoinId;

    private BigDecimal amount;

    private BigDecimal fee;

    private String type;

    private String status;

    private BitpandaTime time;
}
