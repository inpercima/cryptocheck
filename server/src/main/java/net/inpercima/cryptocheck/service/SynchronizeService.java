package net.inpercima.cryptocheck.service;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.FiatTransaction;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsTransactions;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsTransactionsData;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsTransactionsDataAttributes;
import net.inpercima.cryptocheck.repository.FiatTransactionRepository;

@RequiredArgsConstructor
@Service
public class SynchronizeService {

    private final RestService restService;

    private final FiatTransactionRepository fiatTransactionRepository;

    public boolean synchronize() {
        BitpandaFiatWalletsTransactionsData[] datas = restService
                .getData("/fiatwallets/transactions?page_size=500", BitpandaFiatWalletsTransactions.class).getBody()
                .getData();
        Arrays.asList(datas).stream().map(data -> data.getAttributes()).map(a -> convertToEntity(a))
                .forEach(ft -> fiatTransactionRepository.save(ft));
        return true;
    }

    private FiatTransaction convertToEntity(BitpandaFiatWalletsTransactionsDataAttributes object) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(object, FiatTransaction.class);
    }
}
