package net.inpercima.cryptocheck.entity.mapped;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import net.inpercima.cryptocheck.entity.Origin;

@Getter
@Setter
@MappedSuperclass
public class SuperTransaction extends SuperId {

    @Column(length = 10)
    private String type;

    @Column(precision = 15, scale = 8)
    private BigDecimal amount;

    @Column(precision = 15, scale = 8)
    private BigDecimal fee;

    private LocalDateTime date;

    private String status;

    @ManyToOne
    private Origin origin;

    @Column(length = 36)
    private String transactionId;
}
