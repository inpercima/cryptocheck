package net.inpercima.cryptocheck.model.bitpanda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaAssetWallets {

    private BitpandaWalletsData<BitpandaAssetWalletsDataAttributes>[] data;
}
