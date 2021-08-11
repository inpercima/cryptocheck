package net.inpercima.cryptocheck.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.AssetTransaction;
import net.inpercima.cryptocheck.entity.AssetType;
import net.inpercima.cryptocheck.entity.FiatTransaction;
import net.inpercima.cryptocheck.entity.FiatType;
import net.inpercima.cryptocheck.entity.Origin;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWalletsTransactions;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWalletsTransactionsDataAttributes;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsTransactions;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsTransactionsDataAttributes;
import net.inpercima.cryptocheck.repository.AssetTransactionRepository;
import net.inpercima.cryptocheck.repository.FiatTransactionRepository;

@RequiredArgsConstructor
@Service
public class SynchronizeService {

    private final RestService restService;

    private final FiatTransactionRepository fiatTransactionRepository;

    private final AssetTransactionRepository assetTransactionRepository;

    private final List<BitpandaFiatWalletsTransactions> fiatTransactions;

    private final List<BitpandaAssetWalletsTransactions> assetTransactions;

    public void synchronize() {
        collectFiatTransactions("?page=1&page_size=50");
        saveFiatTransactions();

        collectAssetTransactions("?page=1&page_size=50");
        saveAssetTransactions();
    }

    private void collectFiatTransactions(final String pagination) {
        BitpandaFiatWalletsTransactions bfwt = restService
                .getData("/fiatwallets/transactions" + pagination, BitpandaFiatWalletsTransactions.class).getBody();
        fiatTransactions.add(bfwt);
        if (bfwt.getLinks().getNext() != null) {
            collectFiatTransactions(bfwt.getLinks().getNext());
        }
    }

    private void saveFiatTransactions() {
        // firstly reverse transaction parts
        Collections.reverse(fiatTransactions);
        // second reverse data in transaction parts to get first transaction on top
        fiatTransactions.forEach(bfwt -> Collections.reverse(Arrays.asList(bfwt.getData())));

        fiatTransactions.stream().forEach(bfwt -> Arrays.asList(bfwt.getData()).stream().forEach(data -> {
            if (!fiatTransactionRepository.existsByTransactionId(data.getId())) {
                FiatTransaction fiatTransaction = convertToEntity(data.getAttributes());
                final FiatType fiatType = new FiatType();
                fiatType.setId(data.getAttributes().getFiatId());
                fiatTransaction.setFiatType(fiatType);
                fiatTransaction.setTransactionId(data.getId());
                final Origin origin = new Origin();
                origin.setId(1);
                fiatTransaction.setOrigin(origin);
                fiatTransaction.setDate(data.getAttributes().getTime().getDateIso8601());
                fiatTransactionRepository.save(fiatTransaction);
            }
        }));
    }

    private FiatTransaction convertToEntity(BitpandaFiatWalletsTransactionsDataAttributes object) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(object, FiatTransaction.class);
    }

    private void collectAssetTransactions(final String pagination) {
        BitpandaAssetWalletsTransactions bfwt = restService
                .getData("/wallets/transactions" + pagination, BitpandaAssetWalletsTransactions.class).getBody();
        assetTransactions.add(bfwt);
        if (bfwt.getLinks().getNext() != null) {
            collectAssetTransactions(bfwt.getLinks().getNext());
        }
    }

    private void saveAssetTransactions() {
        // firstly reverse transaction parts
        Collections.reverse(assetTransactions);
        // second reverse data in transaction parts to get first transaction on top
        assetTransactions.forEach(bfwt -> Collections.reverse(Arrays.asList(bfwt.getData())));

        assetTransactions.stream().forEach(bfwt -> Arrays.asList(bfwt.getData()).stream().forEach(data -> {
            if (!fiatTransactionRepository.existsByTransactionId(data.getId())) {
                AssetTransaction assetTransaction = convertToEntity(data.getAttributes());
                final AssetType assetType = new AssetType();
                assetType.setId(data.getAttributes().getCryptocoinId());
                assetTransaction.setAssetType(assetType);
                assetTransaction.setTransactionId(data.getId());
                final Origin origin = new Origin();
                origin.setId(1);
                assetTransaction.setOrigin(origin);
                assetTransaction.setDate(data.getAttributes().getTime().getDateIso8601());
                assetTransactionRepository.save(assetTransaction);
            }
        }));
    }

    private AssetTransaction convertToEntity(BitpandaAssetWalletsTransactionsDataAttributes object) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(object, AssetTransaction.class);
    }
}
