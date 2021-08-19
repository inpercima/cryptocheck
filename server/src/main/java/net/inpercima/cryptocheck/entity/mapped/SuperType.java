package net.inpercima.cryptocheck.entity.mapped;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class SuperType {

    @Id
    private Integer id;

    @Column(length = 10)
    private String name;
}
