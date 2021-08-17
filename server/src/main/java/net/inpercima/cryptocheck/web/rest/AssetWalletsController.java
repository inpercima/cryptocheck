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
import net.inpercima.cryptocheck.entity.AssetTransaction;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWallets;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaAssetWalletsDataAttributes;
import net.inpercima.cryptocheck.model.dto.AssetWallet;
import net.inpercima.cryptocheck.repository.AssetTransactionRepository;
import net.inpercima.cryptocheck.service.RestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets/asset")
public class AssetWalletsController {

    private final AssetTransactionRepository assetTransactionRepository;

    private final RestService restService;

    @GetMapping
    public List<AssetWallet> getAllWallets() {
        return Arrays.asList(restService.getData("/wallets", BitpandaAssetWallets.class).getBody().getData()).stream()
                .map(data -> data.getAttributes()).map(a -> convertToDto(a))
                .filter(a -> a.getBalance().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
    }

    private AssetWallet convertToDto(BitpandaAssetWalletsDataAttributes object) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(object, AssetWallet.class);
    }

    @GetMapping("/transactions/unmatched")
    public List<AssetTransaction> findAllUnmatchedTransactionsByAssetType(@RequestParam final String assetType) {
        return assetTransactionRepository.findAllUnmatchedTransactionsByAssetType(assetType);
    }
}
