package net.inpercima.cryptocheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TransactionRepository<T> extends JpaRepository<T, Long> {

    public boolean existsByTransactionId(final String transactionId);
}
