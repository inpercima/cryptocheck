package net.inpercima.cryptocheck.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.inpercima.cryptocheck.entity.Setting;
import net.inpercima.cryptocheck.repository.SettingRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingRepository settingRepository;

    @GetMapping
    public Setting loadSetting() {
        return settingRepository.findById(1).orElse(new Setting());
    }

    @PutMapping
    public Setting saveSetting(@RequestBody Setting entity) {
        return settingRepository.save(entity);
    }
}
