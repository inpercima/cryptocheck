package net.inpercima.cryptocheck.model.bitpanda;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaFiatWalletsDataAttributes {

    @JsonProperty("fiat_symbol")
    private String FiatSymbol;

    private BigDecimal balance;

    private String name;
}
