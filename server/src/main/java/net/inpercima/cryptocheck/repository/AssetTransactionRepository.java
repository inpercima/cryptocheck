package net.inpercima.cryptocheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.inpercima.cryptocheck.entity.AssetTransaction;

public interface AssetTransactionRepository extends JpaRepository<AssetTransaction, Long> {

    public boolean existsByTransactionId(final String transactionId);
}
