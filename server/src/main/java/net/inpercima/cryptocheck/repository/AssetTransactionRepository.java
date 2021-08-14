package net.inpercima.cryptocheck.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.inpercima.cryptocheck.entity.AssetTransaction;

public interface AssetTransactionRepository extends TransactionRepository<AssetTransaction> {

    @Query("SELECT t FROM AssetTransaction t WHERE t.type = 'sell' AND t.status = 'finished'")
    public List<AssetTransaction> findAllFinishedSells();

    @Query("SELECT t FROM AssetTransaction t LEFT JOIN t.assetType at "
            + "WHERE t.type = 'buy' AND t.status = 'finished' AND t.matchId IS NULL AND at.name = ?1 AND t.number = ?2")
    public AssetTransaction findBuyMatchingSell(final String assetType, final BigDecimal number);
}
