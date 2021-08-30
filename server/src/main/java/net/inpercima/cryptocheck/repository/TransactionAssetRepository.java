package net.inpercima.cryptocheck.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.inpercima.cryptocheck.entity.TransactionAsset;

public interface TransactionAssetRepository extends TransactionRepository<TransactionAsset> {

    @Query("SELECT t FROM TransactionAsset t WHERE t.status = 'finished' AND t.relationId IS NULL AND t.type = 'sell'")
    public List<TransactionAsset> findAllUnrelatedFinishedSellTransactions();

    @Query("SELECT t FROM TransactionAsset t LEFT JOIN t.typeAsset ta "
            + "WHERE t.status = 'finished' AND t.relationId IS NULL "
            + "AND ta.name = :assetSymbol AND t.number = :number AND t.type = 'buy' AND t.date <= :date")
    public TransactionAsset findFinishedBuyTransaction(final String assetSymbol, final BigDecimal number,
            final LocalDateTime date);

    @Query("SELECT t FROM TransactionAsset t LEFT JOIN t.typeAsset ta "
            + "WHERE t.status = 'finished' AND t.relationId IS NULL "
            + "AND ta.name = :assetSymbol AND t.type IN ('buy', 'sell') ORDER BY ta.name, t.date, t.type")
    public List<TransactionAsset> findAllUnrelatedTransactions(final String assetSymbol);

    @Query("SELECT t FROM TransactionAsset t LEFT JOIN t.typeAsset ta "
            + "WHERE t.status = 'finished' AND t.relationId IS NULL ORDER BY ta.name, t.date, t.type")
    public List<TransactionAsset> findAllUnrelatedTransactions();

    @Query("SELECT t FROM TransactionAsset t "
            + "WHERE t.relationId IS NOT NULL AND t.date BETWEEN :startDate AND :endDate ORDER BY t.typeAsset, t.date, t.type")
    public List<TransactionAsset> findAllRelatedTransactions(final LocalDateTime startDate,
            final LocalDateTime endDate);
}
