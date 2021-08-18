package net.inpercima.cryptocheck.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.TransactionAsset;
import net.inpercima.cryptocheck.repository.TransactionAssetRepository;

@RequiredArgsConstructor
@Service
public class AnalyzationService {

    private final TransactionAssetRepository transactionAssetRepository;

    public void findRelatedTransactions() {
        transactionAssetRepository.findAllFinishedSells().stream().forEach(sell -> {
            final TransactionAsset buy = transactionAssetRepository.findRelatedBuyTransaction(sell.getTypeAsset().getName(),
                    sell.getNumber());
            if (buy != null) {
                final String uuid = UUID.randomUUID().toString();
                buy.setRelationId(uuid);
                sell.setRelationId(uuid);
                transactionAssetRepository.save(buy);
                transactionAssetRepository.save(sell);
            }
        });
    }
}
