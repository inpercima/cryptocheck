package net.inpercima.cryptocheck.web.rest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.TransactionAsset;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWallets;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWalletsDataAttributes;
import net.inpercima.cryptocheck.model.dto.AssetWallet;
import net.inpercima.cryptocheck.model.dto.TransactionDto;
import net.inpercima.cryptocheck.repository.TransactionAssetRepository;
import net.inpercima.cryptocheck.service.RestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets/asset")
public class AssetWalletsController {

    private final TransactionAssetRepository transactionAssetRepository;

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

    @GetMapping("/transactions/relations/none")
    public List<TransactionDto> findAllUnrelatedTransactions(@RequestParam final String assetSymbol) {
        return transactionAssetRepository.findAllUnrelatedTransactions(assetSymbol).stream()
                .map(t -> convertToTransactionDto(t)).collect(Collectors.toList());
    }

    private TransactionDto convertToTransactionDto(final TransactionAsset object) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(object, TransactionDto.class);
    }
}

// SELECT t1.*
// FROM `asset_transaction` AS `t1`
// JOIN `asset_transaction` AS `t2`
// ON `t1`.`match_id` = `t2`.`match_id` AND `t1`.`id` != `t2`.`id`
// ORDER BY t1.match_id DESC, t1.type ASC;
