package net.inpercima.cryptocheck.model.bitpanda;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaFiatWalletsDataAttributes extends BitpandaWalletsDataAttributes {

    @JsonProperty("fiat_symbol")
    private String fiatSymbol;
}
