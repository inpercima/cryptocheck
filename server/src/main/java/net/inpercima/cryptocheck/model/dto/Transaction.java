package net.inpercima.cryptocheck.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {

    private String type;

    private String symbol;

    private BigDecimal amount;

    private BigDecimal number;

    private LocalDateTime date;
}
