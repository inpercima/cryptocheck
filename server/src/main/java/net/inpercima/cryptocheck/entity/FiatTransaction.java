package net.inpercima.cryptocheck.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FiatTransaction extends Transaction {

    /**
     * type of fiat
     * <p>
     * api response: {@code fiat_id}
     * <p>
     * example: EUR = 1
     */
    @ManyToOne
    private FiatType fiatType;
}
