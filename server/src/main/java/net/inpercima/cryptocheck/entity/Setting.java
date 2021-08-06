package net.inpercima.cryptocheck.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Setting {
    
    @Id
    private Integer id;

    @Column(length = 3)
    private String currency;

    @Column(length = 5)
    private String ticker;

    @Column(length = 5)
    private String fav1;

    @Column(length = 5)
    private String fav2;

    @Column(length = 5)
    private String fav3;

    @Column(length = 5)
    private String fav4;
}