package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaAssetWalletsDataAttributes {

    @JsonProperty("cryptocoin_symbol")
    private String CryptocoinSymbol;

    private BigDecimal balance;

    private String name;
}
