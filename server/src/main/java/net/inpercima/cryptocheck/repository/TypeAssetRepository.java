package net.inpercima.cryptocheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.inpercima.cryptocheck.entity.TypeAsset;

public interface TypeAssetRepository extends JpaRepository<TypeAsset, Long> { }
