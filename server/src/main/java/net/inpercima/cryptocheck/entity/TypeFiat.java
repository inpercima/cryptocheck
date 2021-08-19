package net.inpercima.cryptocheck.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import net.inpercima.cryptocheck.entity.mapped.SuperType;

@Entity
@Getter
@Setter
public class TypeFiat extends SuperType { }
