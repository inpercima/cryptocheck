package net.inpercima.cryptocheck.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.inpercima.cryptocheck.entity.TransactionAsset;

public interface TransactionAssetRepository extends TransactionRepository<TransactionAsset> {

    @Query("SELECT t FROM TransactionAsset t WHERE t.type = 'sell' AND t.status = 'finished'")
    public List<TransactionAsset> findAllFinishedSells();

    @Query("SELECT t FROM TransactionAsset t LEFT JOIN t.typeAsset ta "
            + "WHERE t.type = 'buy' AND t.status = 'finished' AND ta.name = ?1 AND t.number = ?2")
    public TransactionAsset findRelatedTransactions(final String assetName, final BigDecimal number);

    @Query("SELECT t FROM TransactionAsset t LEFT JOIN t.typeAsset ta WHERE ta.name = ?1 ORDER BY t.date DESC")
    public List<TransactionAsset> findAllUnmatchedTransactionsByTypeAsset(final String assetName);
}
