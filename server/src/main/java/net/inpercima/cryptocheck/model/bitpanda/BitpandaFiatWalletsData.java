package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaFiatWalletsData {

    private String type;

    private BitpandaFiatWalletsDataAttributes attributes;
}
