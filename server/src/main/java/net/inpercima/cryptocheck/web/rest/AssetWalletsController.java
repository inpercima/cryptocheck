package net.inpercima.cryptocheck.web.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.TransactionAsset;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWallets;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWalletsDataAttributes;
import net.inpercima.cryptocheck.model.dto.AssetWallet;
import net.inpercima.cryptocheck.model.dto.Transaction;
import net.inpercima.cryptocheck.repository.TransactionAssetRepository;
import net.inpercima.cryptocheck.service.RestService;
import net.inpercima.cryptocheck.service.TransactionAssetService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets/asset")
public class AssetWalletsController {

    private final TransactionAssetRepository transactionAssetRepository;

    private final TransactionAssetService transactionAssetService;

    private final RestService restService;

    @GetMapping
    public List<AssetWallet> getAllWallets() {
        return Arrays.asList(restService.getData("/wallets", BitpandaAssetWallets.class).getBody().getData()).stream()
                .map(data -> data.getAttributes()).map(a -> convertToAssetWallet(a))
                .filter(a -> a.getBalance().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
    }

    private AssetWallet convertToAssetWallet(final BitpandaAssetWalletsDataAttributes object) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(object, AssetWallet.class);
    }

    @GetMapping("/transactions/relations/{year}")
    public List<List<Transaction>> findAllRelatedTransactions(@PathVariable final int year) {
        final List<Transaction> internal = new ArrayList<>();
        final List<List<Transaction>> transactions = new ArrayList<>();
        String lastRelationId = "";
        List<TransactionAsset> transactionAssets = transactionAssetService.findAllRelatedTransactions(year);
        // stream not possible b/c of "Local variable lastRelationId defined in an
        // enclosing scope must be final or effectively final"
        for (TransactionAsset transactionAsset : transactionAssets) {
            final Transaction transaction = convertToTransactionDto(transactionAsset);
            if (lastRelationId.equals(transactionAsset.getRelationId())) {
                internal.add(transaction);
            } else {
                if (!internal.isEmpty()) {
                    List<Transaction> copy = new ArrayList<>(internal);
                    transactions.add(copy);
                    internal.clear();
                }
                lastRelationId = transactionAsset.getRelationId();
                internal.add(transaction);
            }
        }
        return transactions;
    }

    @GetMapping("/transactions/relations/none/{assetSymbol}")
    public List<Transaction> findAllUnrelatedTransactions(@PathVariable final String assetSymbol) {
        return transactionAssetRepository.findAllUnrelatedTransactions(assetSymbol).stream()
                .map(t -> convertToTransactionDto(t)).collect(Collectors.toList());
    }

    @GetMapping("/transactions/relations/none")
    public List<Transaction> findAllUnrelatedTransactions() {
        return transactionAssetRepository.findAllUnrelatedTransactions().stream().map(t -> convertToTransactionDto(t))
                .collect(Collectors.toList());
    }

    private Transaction convertToTransactionDto(final TransactionAsset object) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final Transaction transaction = modelMapper.map(object, Transaction.class);
        transaction.setSymbol(object.getTypeAsset().getName());
        return transaction;

    }
}

// SELECT t1.*
// FROM `asset_transaction` AS `t1`
// JOIN `asset_transaction` AS `t2`
// ON `t1`.`match_id` = `t2`.`match_id` AND `t1`.`id` != `t2`.`id`
// ORDER BY t1.match_id DESC, t1.type ASC;
