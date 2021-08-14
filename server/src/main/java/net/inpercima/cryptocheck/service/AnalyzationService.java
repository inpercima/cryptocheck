package net.inpercima.cryptocheck.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.AssetTransaction;
import net.inpercima.cryptocheck.repository.AssetTransactionRepository;

@RequiredArgsConstructor
@Service
public class AnalyzationService {

    private final AssetTransactionRepository assetTransactionRepository;

    public void analyse() {
        assetTransactionRepository.findAllFinishedSells().stream().forEach(sell -> {
            final AssetTransaction buy = assetTransactionRepository.findBuyMatchingSell(sell.getAssetType().getName(),
                    sell.getNumber());
            if (buy != null) {
                final String uuid = UUID.randomUUID().toString();
                buy.setMatchId(uuid);
                sell.setMatchId(uuid);
                assetTransactionRepository.save(buy);
                assetTransactionRepository.save(sell);
            }
        });
    }
}
