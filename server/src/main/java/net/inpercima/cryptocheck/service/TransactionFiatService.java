package net.inpercima.cryptocheck.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.TransactionFiat;
import net.inpercima.cryptocheck.entity.TypeFiat;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsTransactions;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsTransactionsDataAttributes;
import net.inpercima.cryptocheck.repository.TransactionFiatRepository;
import net.inpercima.cryptocheck.repository.OriginRepository;

@RequiredArgsConstructor
@Service
public class TransactionFiatService {

    private final RestService restService;

    private final TransactionFiatRepository fiatTransactionRepository;

    private final OriginRepository originRepository;

    private final List<BitpandaFiatWalletsTransactions> transactions;

    public void synchronize() {
        collect("?page=1&page_size=50");
        save();
    }

    private void collect(final String pagination) {
        BitpandaFiatWalletsTransactions bwt = restService
                .getData("/fiatwallets/transactions" + pagination, BitpandaFiatWalletsTransactions.class).getBody();
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
            if (!fiatTransactionRepository.existsByTransactionId(data.getId())) {
                final TransactionFiat transaction = convertToEntity(data.getAttributes());
                final TypeFiat typeFiat = new TypeFiat();
                typeFiat.setId(data.getAttributes().getFiatId());
                transaction.setTypeFiat(typeFiat);
                transaction.setTransactionId(data.getId());
                transaction.setOrigin(originRepository.getByName("Bitpanda"));
                transaction.setDate(data.getAttributes().getTime().getDateIso8601());
                fiatTransactionRepository.save(transaction);
            }
        }));
    }

    private TransactionFiat convertToEntity(final BitpandaFiatWalletsTransactionsDataAttributes object) {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(object, TransactionFiat.class);
    }
}
