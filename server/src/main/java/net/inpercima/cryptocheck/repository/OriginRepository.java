package net.inpercima.cryptocheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.inpercima.cryptocheck.entity.Origin;

public interface OriginRepository extends JpaRepository<Origin, Long> {

    public Origin getByName(final String name);
}
