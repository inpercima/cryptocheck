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
    public void findSingleBuyAndSellRelatedTransactions() {
        transactionAssetRepository.findAllFinishedSells().stream().forEach(sell -> {
            final TransactionAsset buy = transactionAssetRepository
                    .findRelatedBuyTransaction(sell.getTypeAsset().getName(), sell.getNumber());
            if (buy != null) {
                final String uuid = UUID.randomUUID().toString();
                buy.setRelationId(uuid);
                sell.setRelationId(uuid);
                transactionAssetRepository.save(buy);
                transactionAssetRepository.save(sell);
            }
        });
    }

    public void findComplexBuyAndSellRelatedTransactions() {
        BigDecimal compensation = BigDecimal.ZERO;
        final List<TransactionAsset> buyList = new ArrayList<>();
        final List<TransactionAsset> wdList = new ArrayList<>();
        List<TransactionAsset> transactions = transactionAssetRepository.findAllUnrelatedTransactions();
        for (TransactionAsset transaction : transactions) {
            final String transactionSymbol = transaction.getTypeAsset().getName();
            final String buySymbol = !buyList.isEmpty() ? buyList.get(0).getTypeAsset().getName() : "";
            if ("buy".equals(transaction.getType())
                    && (buyList.isEmpty() || (!buyList.isEmpty() && transactionSymbol.equals(buySymbol)))) {
                buyList.add(transaction);
            } else if ("sell".equals(transaction.getType()) && !buyList.isEmpty()
                    && transactionSymbol.equals(buySymbol)) {
                final BigDecimal numbers = buyList.stream().map(t -> t.getNumber())
                        .reduce(BigDecimal.ZERO, BigDecimal::add).subtract(compensation);
                if (transaction.getNumber().subtract(numbers).compareTo(BigDecimal.ZERO) == 0) {
                    final String uuid = UUID.randomUUID().toString();
                    buyList.forEach(buy -> {
                        buy.setRelationId(uuid);
                        transactionAssetRepository.save(buy);
                    });
                    wdList.forEach(wd -> {
                        wd.setRelationId(uuid);
                        transactionAssetRepository.save(wd);
                    });
                    transaction.setRelationId(uuid);
                    transactionAssetRepository.save(transaction);
                    buyList.clear();
                    compensation = BigDecimal.ZERO;
                }
            } else if (("withdrawal".equals(transaction.getType()) || "deposit".equals(transaction.getType()))
                    && !buyList.isEmpty()) {
                wdList.add(transaction);
                compensation = compensation.add(transaction.getFee());
            } else {
                buyList.clear();
                buyList.add(transaction);
                compensation = BigDecimal.ZERO;
            }
        }
    }
}
