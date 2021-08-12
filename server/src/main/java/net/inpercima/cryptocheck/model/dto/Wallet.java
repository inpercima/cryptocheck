package net.inpercima.cryptocheck.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wallet {

    private String symbol;

    private BigDecimal balance;

    private String name;
}
