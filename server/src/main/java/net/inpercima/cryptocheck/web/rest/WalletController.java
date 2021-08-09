package net.inpercima.cryptocheck.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.service.RestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WalletController {

    private final RestService restService;

    @GetMapping
    public Object listAll() {
        return restService.getData("/wallets", Object.class);
    }
}
