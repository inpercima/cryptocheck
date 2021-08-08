package net.inpercima.cryptocheck.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.service.RestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WalletController {

    @Value("${app.bitpanda.apikey}")
    private String bitpandaApiKey;

    private final RestService restService;

    @GetMapping
    public Object listAll() {
        return restService.getEntity("https://api.bitpanda.com/v1/wallets", bitpandaApiKey, Object.class);
    }
}
//https://api.bitpanda.com/v1/fiatwallets/transactions?page_size=500
