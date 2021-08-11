package net.inpercima.cryptocheck.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiatWallet {

    private String symbol;

    private BigDecimal balance;

    private String name;
}
