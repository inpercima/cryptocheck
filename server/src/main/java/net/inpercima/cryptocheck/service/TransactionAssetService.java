package net.inpercima.cryptocheck.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.TransactionAsset;
import net.inpercima.cryptocheck.entity.TypeAsset;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWalletsTransactions;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWalletsTransactionsDataAttributes;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaBfcAttributes;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaTrade;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaTradeAttributes;
import net.inpercima.cryptocheck.repository.OriginRepository;
import net.inpercima.cryptocheck.repository.TransactionAssetRepository;

@RequiredArgsConstructor
@Service
public class TransactionAssetService {

    private final RestService restService;

    private final TransactionAssetRepository transactionAssetRepository;

    private final OriginRepository originRepository;

    private final List<BitpandaAssetWalletsTransactions> transactions;

    public void synchronize() {
        collect("?page=1&page_size=50");
        save();
    }

    private void collect(final String pagination) {
        BitpandaAssetWalletsTransactions bwt = restService
                .getData("/wallets/transactions" + pagination, BitpandaAssetWalletsTransactions.class).getBody();
        transactions.add(bwt);
        if (bwt.getLinks().getNext() != null) {
            collect(bwt.getLinks().getNext());
        }
    }

    private void save() {
        // firstly reverse transaction parts
        Collections.reverse(transactions);
        // second reverse data in transaction parts to get first transaction on top
        transactions.forEach(bwt -> Collections.reverse(Arrays.asList(bwt.getData())));

        transactions.stream().forEach(bwt -> Arrays.asList(bwt.getData()).stream().forEach(data -> {
            if (!transactionAssetRepository.existsByTransactionId(data.getId())) {
                final BitpandaAssetWalletsTransactionsDataAttributes attributes = data.getAttributes();
                final TransactionAsset transaction = convertToEntity(attributes);
                final TypeAsset typeAsset = new TypeAsset();
                typeAsset.setId(attributes.getCryptocoinId());
                transaction.setTypeAsset(typeAsset);
                transaction.setTransactionId(data.getId());
                transaction.setOrigin(originRepository.getByName("Bitpanda"));
                transaction.setDate(attributes.getTime().getDateIso8601());
                // reset amount b/c amount is set as number by the mapper
                if (attributes.isBfc()) {
                    final BitpandaBfcAttributes bfcAttributes = attributes.getBestFeeCollection().getAttributes();
                    transaction.setAmount(bfcAttributes.getBfcMarketValueEur());
                    transaction.setPrice(bfcAttributes.getBestCurrentPriceEur());
                    transaction.setRefTradeId(bfcAttributes.getRelatedTrade().getId());
                } else {
                    transaction.setAmount(attributes.getAmountEur());
                    final BitpandaTrade trade = attributes.getTrade();
                    if (trade != null) {
                        final BitpandaTradeAttributes tradeAttributes = trade.getAttributes();
                        transaction.setTradeId(trade.getId());
                        transaction.setPrice(tradeAttributes.getPrice());
                    }
                }
                transaction.setNumber(attributes.getAmount());
                transactionAssetRepository.save(transaction);
            }
        }));
    }

    private TransactionAsset convertToEntity(final BitpandaAssetWalletsTransactionsDataAttributes object) {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(object, TransactionAsset.class);
    }

    public List<TransactionAsset> findAllUnrelatedTransactions(final String assetSymbol) {
        return transactionAssetRepository.findAllUnrelatedTransactions(assetSymbol);
    }
}
