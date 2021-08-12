package net.inpercima.cryptocheck.entity.mapped;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import net.inpercima.cryptocheck.entity.Origin;

@Getter
@Setter
@MappedSuperclass
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 10)
    private String type;

    @Column(precision = 15, scale = 8)
    private BigDecimal amount;

    @Column(precision = 15, scale = 8)
    private BigDecimal fee;

    private LocalDateTime date;

    private String status;

    @Column(length = 36)
    private String transactionId;

    @ManyToOne
    private Origin origin;
}
