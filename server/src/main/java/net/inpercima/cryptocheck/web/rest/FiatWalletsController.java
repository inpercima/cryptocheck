package net.inpercima.cryptocheck.web.rest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWalletsDataAttributes;
import net.inpercima.cryptocheck.entity.Setting;
import net.inpercima.cryptocheck.model.bitpanda.BitpandaFiatWallets;
import net.inpercima.cryptocheck.model.dto.FiatWallet;
import net.inpercima.cryptocheck.repository.SettingRepository;
import net.inpercima.cryptocheck.service.RestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets/fiat")
public class FiatWalletsController {

    private final RestService restService;

    private final SettingRepository settingRepository;

    @GetMapping
    public List<FiatWallet> getAllWallets() {
        Optional<Setting> setting = settingRepository.findById(1);
        return Arrays.asList(restService.getData("/fiatwallets", BitpandaFiatWallets.class).getBody().getData())
                .stream().map(data -> data.getAttributes()).map(a -> convertToDto(a))
                .filter(a -> a.getBalance().compareTo(BigDecimal.ZERO) > 0
                        || setting.get().getCurrency().equals(a.getSymbol()))
                .collect(Collectors.toList());
    }

    private FiatWallet convertToDto(BitpandaFiatWalletsDataAttributes object) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(object, FiatWallet.class);
    }
}
