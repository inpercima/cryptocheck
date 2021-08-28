package net.inpercima.cryptocheck.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.inpercima.cryptocheck.entity.TransactionAsset;

public interface TransactionAssetRepository extends TransactionRepository<TransactionAsset> {

        @Query("SELECT t FROM TransactionAsset t WHERE t.type = 'sell' AND t.status = 'finished'")
        public List<TransactionAsset> findAllFinishedSells();

        @Query("SELECT t FROM TransactionAsset t LEFT JOIN t.typeAsset ta "
                        + "WHERE t.type = 'buy' AND t.status = 'finished' AND ta.name = :assetSymbol AND t.number = :number")
        public TransactionAsset findRelatedBuyTransaction(final String assetSymbol, final BigDecimal number);

        @Query("SELECT t FROM TransactionAsset t LEFT JOIN t.typeAsset ta "
                        + "WHERE t.relationId IS NULL AND t.status = 'finished' "
                        + "AND ta.name = :assetSymbol AND t.type IN ('buy', 'sell') ORDER BY ta.name, t.date, t.type")
        public List<TransactionAsset> findAllUnrelatedTransactions(final String assetSymbol);

        @Query("SELECT t FROM TransactionAsset t LEFT JOIN t.typeAsset ta "
                        + "WHERE t.relationId IS NULL AND t.status = 'finished' ORDER BY ta.name, t.date, t.type")
        public List<TransactionAsset> findAllUnrelatedTransactions();

        @Query("SELECT t FROM TransactionAsset t "
                        + "WHERE t.relationId IS NOT NULL AND t.date BETWEEN :startDate AND :endDate ORDER BY t.typeAsset, t.date, t.type")
        public List<TransactionAsset> findAllRelatedTransactions(final LocalDateTime startDate,
                        final LocalDateTime endDate);
}
