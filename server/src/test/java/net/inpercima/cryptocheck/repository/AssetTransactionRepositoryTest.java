package net.inpercima.cryptocheck.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import net.inpercima.cryptocheck.entity.TransactionAsset;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AssetTransactionRepositoryTest {

    @Inject
    private TransactionAssetRepository assetTransactionRepository;

    @Test
    void findAllFinishedSells() {
        List<TransactionAsset> transactions = assetTransactionRepository.findAllUnrelatedFinishedSellTransactions();
        assertThat(transactions).isNotEmpty();
    }

    @Test
    void findAllBuysMatchingSells() {
        TransactionAsset transaction = assetTransactionRepository.findFinishedBuyTransaction("LINK",
                BigDecimal.valueOf(77.41533576), LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        assertThat(transaction).isNotNull();
    }

    @Test
    void findAllByAssetTypeOrderByDateDesc() {
        List<TransactionAsset> transactions = assetTransactionRepository.findAllUnrelatedTransactions();
        assertThat(transactions).isNotEmpty();
    }
}
