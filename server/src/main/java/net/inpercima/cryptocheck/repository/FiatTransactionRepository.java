package net.inpercima.cryptocheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.inpercima.cryptocheck.entity.FiatTransaction;

public interface FiatTransactionRepository extends JpaRepository<FiatTransaction, Long> { }
