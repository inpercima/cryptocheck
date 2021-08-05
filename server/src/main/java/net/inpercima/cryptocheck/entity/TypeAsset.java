package net.inpercima.cryptocheck.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TypeAsset {
    
    @Id
    private Integer id;

    @Column(length = 10)
    private String name;
}