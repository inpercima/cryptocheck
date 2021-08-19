package net.inpercima.cryptocheck.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import net.inpercima.cryptocheck.entity.mapped.SuperTransaction;

@Entity
@Getter
@Setter
public class TransactionAsset extends SuperTransaction {

    @ManyToOne
    private TypeAsset typeAsset;

    @Column(precision = 15, scale = 8)
    private BigDecimal number;

    @Column(precision = 15, scale = 8)
    private BigDecimal price;

    @Column(length = 36)
    private String tradeId;

    @Column(length = 36)
    private String refTradeId;

    @Column(length = 36)
    private String relationId;
}
