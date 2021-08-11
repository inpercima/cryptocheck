package net.inpercima.cryptocheck.model.bitpanda;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaAssetWalletsDataAttributes extends BitpandaWalletsDataAttributes {

    @JsonProperty("cryptocoin_symbol")
    private String cryptocoinSymbol;
}
