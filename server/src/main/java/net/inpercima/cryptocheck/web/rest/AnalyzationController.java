package net.inpercima.cryptocheck.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.service.AnalyzationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/analyzation")
public class AnalyzationController {

    private final AnalyzationService analyzationService;

    @GetMapping
    public void analyze() {
        analyzationService.analyze();
    }
}
