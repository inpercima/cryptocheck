package net.inpercima.cryptocheck.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.TypeAsset;
import net.inpercima.cryptocheck.repository.TypeAssetRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class AssetsController {

    private final TypeAssetRepository assetTypeRepository;

    @GetMapping
    public List<TypeAsset> findAll() {
        return assetTypeRepository.findAll();
    }
}
