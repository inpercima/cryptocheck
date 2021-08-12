package net.inpercima.cryptocheck.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.FiatTransaction;
import net.inpercima.cryptocheck.entity.FiatType;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsTransactions;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsTransactionsDataAttributes;
import net.inpercima.cryptocheck.repository.FiatTransactionRepository;
import net.inpercima.cryptocheck.repository.OriginRepository;

@RequiredArgsConstructor
@Service
public class FiatTransactionsService {

    private final RestService restService;

    private final FiatTransactionRepository fiatTransactionRepository;

    private final OriginRepository originRepository;

    private final List<BitpandaFiatWalletsTransactions> fiatTransactions;

    public void synchronize() {
        collectTransactions("?page=1&page_size=50");
        saveTransactions();
    }

    private void collectTransactions(final String pagination) {
        BitpandaFiatWalletsTransactions bwt = restService
                .getData("/fiatwallets/transactions" + pagination, BitpandaFiatWalletsTransactions.class).getBody();
        fiatTransactions.add(bwt);
        if (bwt.getLinks().getNext() != null) {
            collectTransactions(bwt.getLinks().getNext());
        }
    }

    private void saveTransactions() {
        // firstly reverse transaction parts
        Collections.reverse(fiatTransactions);
        // second reverse data in transaction parts to get first transaction on top
        fiatTransactions.forEach(bwt -> Collections.reverse(Arrays.asList(bwt.getData())));

        fiatTransactions.stream().forEach(bwt -> Arrays.asList(bwt.getData()).stream().forEach(data -> {
            if (!fiatTransactionRepository.existsByTransactionId(data.getId())) {
                final FiatTransaction fiatTransaction = convertToEntity(data.getAttributes());
                final FiatType fiatType = new FiatType();
                fiatType.setId(data.getAttributes().getFiatId());
                fiatTransaction.setFiatType(fiatType);
                fiatTransaction.setTransactionId(data.getId());
                fiatTransaction.setOrigin(originRepository.getByName("Bitpanda"));
                fiatTransaction.setDate(data.getAttributes().getTime().getDateIso8601());
                fiatTransactionRepository.save(fiatTransaction);
            }
        }));
    }

    private FiatTransaction convertToEntity(final BitpandaFiatWalletsTransactionsDataAttributes object) {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(object, FiatTransaction.class);
    }
}
