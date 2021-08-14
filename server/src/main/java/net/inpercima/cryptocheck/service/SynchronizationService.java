package net.inpercima.cryptocheck.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SynchronizationService {

    private final AssetTransactionsService assetTransactionsService;

    private final FiatTransactionsService fiatTransactionsService;

    private final AnalyzationService analyzationService;

    public void synchronize() {
        assetTransactionsService.synchronize();
        fiatTransactionsService.synchronize();
        analyzationService.analyse();
    }
}
