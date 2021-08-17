package net.inpercima.cryptocheck.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import net.inpercima.cryptocheck.entity.AssetTransaction;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AssetTransactionRepositoryTest {

    @Inject
    private AssetTransactionRepository assetTransactionRepository;

    @Test
    void findAllFinishedSells() {
        List<AssetTransaction> transactions = assetTransactionRepository.findAllFinishedSells();
        assertThat(transactions).isNotEmpty();
    }

    @Test
    void findAllBuysMatchingSells() {
        AssetTransaction transaction = assetTransactionRepository.findBuyMatchingSell("LINK",
                BigDecimal.valueOf(77.41533576));
        assertThat(transaction).isNotNull();
    }

    @Test
    void findAllByAssetTypeOrderByDateDesc() {
        List<AssetTransaction> transactions = assetTransactionRepository.findAllUnmatchedTransactionsByAssetType("BTC");
        assertThat(transactions).isNotEmpty();
    }
}
