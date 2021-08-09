package net.inpercima.cryptocheck.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.AssetType;
import net.inpercima.cryptocheck.repository.AssetTypeRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class AssetsController {

    private final AssetTypeRepository assetTypeRepository;

    @GetMapping
    public List<AssetType> findAll() {
        return assetTypeRepository.findAll();
    }
}
