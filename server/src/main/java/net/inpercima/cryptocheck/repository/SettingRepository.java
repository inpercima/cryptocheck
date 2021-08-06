package net.inpercima.cryptocheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.inpercima.cryptocheck.entity.Setting;

public interface SettingRepository extends JpaRepository<Setting, Integer> { }
