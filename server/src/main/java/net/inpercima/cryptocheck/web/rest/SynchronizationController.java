package net.inpercima.cryptocheck.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.service.SynchronizationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/synchronization")
public class SynchronizationController {

    private final SynchronizationService synchronizationService;

    @GetMapping
    public void synchronize() {
        synchronizationService.synchronize();
    }
}
