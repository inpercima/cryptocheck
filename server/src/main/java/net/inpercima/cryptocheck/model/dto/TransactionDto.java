package net.inpercima.cryptocheck.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

    private String type;

    private BigDecimal amount;
}
