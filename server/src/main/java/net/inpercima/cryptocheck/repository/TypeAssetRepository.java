package net.inpercima.cryptocheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.inpercima.cryptocheck.entity.AssetType;

public interface TypeAssetRepository extends JpaRepository<AssetType, Long> { }
