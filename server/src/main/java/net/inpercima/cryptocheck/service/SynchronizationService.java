package net.inpercima.cryptocheck.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SynchronizationService {

    private final TransactionAssetService transactionAssetService;

    private final TransactionFiatService transactionFiatService;

    private final AnalyzationService analyzationService;

    public void synchronize() {
        transactionAssetService.synchronize();
        transactionFiatService.synchronize();
        analyzationService.analyse();
    }
}
