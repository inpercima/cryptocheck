package net.inpercima.cryptocheck.entity;

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

@Getter
@Setter
@MappedSuperclass
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * type of transaction
     * <p>
     * api response: {@code type}
     * <p>
     * asset: buy, sell, deposit, withdrawal, transfer, refund, ico
     * <p>
     * fiat: deposit, withdrawal, transfer, refund <br>
     * example: deposit
     */
    @Column(length = 10)
    private String type;

    /**
     * amount in eur of transaction
     * <p>
     * api response:
     * <p>
     * asset: {@code amount_eur} or if best fee collection
     * {@code bfc_market_value_eur}
     * <p>
     * fiat: {@code amount}
     * <p>
     * example: 100.00
     */
    private BigDecimal amount;

    /**
     * fee of a transaction
     * <p>
     * api response: {@code fee}
     * <p>
     * example: 2.00
     */
    private BigDecimal fee;

    /**
     * date of transaction
     * <p>
     * api response: {@code date}
     * <p>
     * example: 2021-06-06 16:01:12
     */
    private LocalDateTime date;

    /**
     * status of transaction
     * <p>
     * api response: {@code status}
     * <p>
     * asset: pending, processing, unconfirmed_transaction_out, open_invitation,
     * finished, canceled
     * <p>
     * fiat: pending, processing, finished, canceled
     * <p>
     * example: finished
     */
    private BigDecimal status;

    /**
     * id of transaction
     * <p>
     * api response: {@code transaction_id}
     * <p>
     * example: 1bb2862a-8b15-4208-d7aa-a236a04e0ad0
     */
    private BigDecimal transaction_id;

    @ManyToOne
    private Origin origin;
}
