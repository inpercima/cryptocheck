package net.inpercima.cryptocheck.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SynchronizationService {

    private final AssetTransactionsService assetTransactionsService;

    private final FiatTransactionsService fiatTransactionsService;

    public void synchronize() {
        assetTransactionsService.synchronize();
        fiatTransactionsService.synchronize();
    }
}
