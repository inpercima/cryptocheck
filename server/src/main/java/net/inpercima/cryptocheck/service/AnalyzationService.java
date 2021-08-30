package net.inpercima.cryptocheck.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.TransactionAsset;
import net.inpercima.cryptocheck.repository.TransactionAssetRepository;

@RequiredArgsConstructor
@Service
public class AnalyzationService {

    private final TransactionAssetRepository transactionAssetRepository;

    public void analyze() {
        findSingleBuyAndSellRelatedTransactions();
        findComplexRelatedTransactions();
    }

    /**
     * Finds a sell and buy by same asset and same number and relate them if the
     * date of the buy is before the date of the sell and both are finished and not
     * in a relation.
     * <p>
     * Example:
     * <p>
     * Buy on 2021-01-01 1.234 BTC
     * <p>
     * Sell on 2021-03.04 1.234 BTC
     */
    public void findSingleBuyAndSellRelatedTransactions() {
        transactionAssetRepository.findAllUnrelatedFinishedSellTransactions().stream().forEach(sell -> {
            final TransactionAsset buy = transactionAssetRepository
                    .findFinishedBuyTransaction(sell.getTypeAsset().getName(), sell.getNumber(), sell.getDate());
            if (buy != null) {
                final String uuid = UUID.randomUUID().toString();
                buy.setRelationId(uuid);
                sell.setRelationId(uuid);
                transactionAssetRepository.save(buy);
                transactionAssetRepository.save(sell);
            }
        });
    }

    /**
     * Finds transactions by same asset and in combination same number and relate
     * them if all are finished and not in a relation.
     */
    public void findComplexRelatedTransactions() {
        BigDecimal compensation = BigDecimal.ZERO;
        final List<TransactionAsset> possibleRelations = new ArrayList<>();
        List<TransactionAsset> transactions = transactionAssetRepository.findAllUnrelatedTransactions();
        for (TransactionAsset transaction : transactions) {
            if (isTypeAndSameAsset("buy", possibleRelations, transaction)) {
                possibleRelations.add(transaction);
            } else if (isTypeAndSameAsset("sell", possibleRelations, transaction)) {
                // possibleRelations can also include withdrawal and deposit. Numbers includes
                // buys only, the rest will be substracted.
                final BigDecimal numbers = possibleRelations.stream().filter(t -> "buy".equals(t.getType()))
                        .map(t -> t.getNumber()).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(compensation);
                if (transaction.getNumber().subtract(numbers).compareTo(BigDecimal.ZERO) == 0) {
                    final String uuid = UUID.randomUUID().toString();
                    // all possible relations will be related
                    possibleRelations.forEach(t -> {
                        t.setRelationId(uuid);
                        transactionAssetRepository.save(t);
                    });
                    transaction.setRelationId(uuid);
                    transactionAssetRepository.save(transaction);
                    possibleRelations.clear();
                    compensation = BigDecimal.ZERO;
                }
            } else if ((isType("withdrawal", transaction) || isType("deposit", transaction))
                    && isSameAsset(possibleRelations, transaction)) {
                possibleRelations.add(transaction);
                compensation = compensation.add(transaction.getFee());
            } else {
                possibleRelations.clear();
                possibleRelations.add(transaction);
                compensation = BigDecimal.ZERO;
            }
        }
    }

    private boolean isTypeAndSameAsset(final String type, final List<TransactionAsset> possibleRelations,
            final TransactionAsset transactionAsset) {
        return isType(type, transactionAsset) && isSameAsset(possibleRelations, transactionAsset);
    }

    private boolean isType(final String type, final TransactionAsset transactionAsset) {
        return type.equals(transactionAsset.getType());
    }

    private boolean isSameAsset(final List<TransactionAsset> possibleRelations,
            final TransactionAsset transactionAsset) {
        final String symbol = !possibleRelations.isEmpty() ? possibleRelations.get(0).getTypeAsset().getName() : "";
        return !possibleRelations.isEmpty() && transactionAsset.getTypeAsset().getName().equals(symbol);
    }
}
