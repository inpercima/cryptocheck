package net.inpercima.cryptocheck.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import net.inpercima.cryptocheck.entity.mapped.Transaction;

@Entity
@Getter
@Setter
public class TransactionFiat extends Transaction {

    @ManyToOne
    private TypeFiat typeFiat;
}
