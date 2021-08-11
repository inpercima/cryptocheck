package net.inpercima.cryptocheck.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import net.inpercima.cryptocheck.entity.mapped.Transaction;

@Entity
@Getter
@Setter
public class AssetTransaction extends Transaction {

    /**
     * type of asset
     * <p>
     * api response: {@code cryptocoin_id}
     * <p>
     * example: BTC = 1, ETH = 5
     */
    @ManyToOne
    private AssetType assetType;

    /**
     * number of traded assets
     * <p>
     * api response: {@code amount}
     * <p>
     * example: 77.41533576
     */
    @Column(precision = 15, scale = 8)
    private BigDecimal number;

    /**
     * price in eur of traded assets
     * <p>
     * api response: {@code price}
     * <br>
     * example: 0.08330000
     */
    @Column(precision = 15, scale = 8)
    private BigDecimal price;
}
