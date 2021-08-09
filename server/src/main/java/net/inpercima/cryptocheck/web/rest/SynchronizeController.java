package net.inpercima.cryptocheck.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.service.SynchronizeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/synchronize")
public class SynchronizeController {

    private final SynchronizeService synchronizeService;

    @GetMapping
    public Object synchronize() {
        return synchronizeService.synchronize();
    }
}
