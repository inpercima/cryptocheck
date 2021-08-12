package net.inpercima.cryptocheck.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.AssetTransaction;
import net.inpercima.cryptocheck.entity.AssetType;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWalletsTransactions;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWalletsTransactionsDataAttributes;
import net.inpercima.cryptocheck.repository.AssetTransactionRepository;
import net.inpercima.cryptocheck.repository.OriginRepository;

@RequiredArgsConstructor
@Service
public class AssetTransactionsService {

    private final RestService restService;

    private final AssetTransactionRepository assetTransactionRepository;

    private final OriginRepository originRepository;

    private final List<BitpandaAssetWalletsTransactions> assetTransactions;

    public void synchronize() {
        collectTransactions("?page=1&page_size=50");
        saveTransactions();
    }

    private void collectTransactions(final String pagination) {
        BitpandaAssetWalletsTransactions bwt = restService
                .getData("/wallets/transactions" + pagination, BitpandaAssetWalletsTransactions.class).getBody();
        assetTransactions.add(bwt);
        if (bwt.getLinks().getNext() != null) {
            collectTransactions(bwt.getLinks().getNext());
        }
    }

    private void saveTransactions() {
        // firstly reverse transaction parts
        Collections.reverse(assetTransactions);
        // second reverse data in transaction parts to get first transaction on top
        assetTransactions.forEach(bwt -> Collections.reverse(Arrays.asList(bwt.getData())));

        assetTransactions.stream().forEach(bwt -> Arrays.asList(bwt.getData()).stream().forEach(data -> {
            if (!assetTransactionRepository.existsByTransactionId(data.getId())) {
                final AssetTransaction assetTransaction = convertToEntity(data.getAttributes());
                final AssetType assetType = new AssetType();
                assetType.setId(data.getAttributes().getCryptocoinId());
                assetTransaction.setAssetType(assetType);
                assetTransaction.setTransactionId(data.getId());
                assetTransaction.setOrigin(originRepository.getByName("Bitpanda"));
                assetTransaction.setDate(data.getAttributes().getTime().getDateIso8601());
                assetTransactionRepository.save(assetTransaction);
            }
        }));
    }

    private AssetTransaction convertToEntity(final BitpandaAssetWalletsTransactionsDataAttributes object) {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(object, AssetTransaction.class);
    }
}
