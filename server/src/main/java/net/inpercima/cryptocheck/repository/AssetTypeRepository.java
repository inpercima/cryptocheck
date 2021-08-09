package net.inpercima.cryptocheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.inpercima.cryptocheck.entity.AssetType;

public interface AssetTypeRepository extends JpaRepository<AssetType, Long> { }
