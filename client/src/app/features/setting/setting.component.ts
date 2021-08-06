import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { SettingService } from './setting.service';
import { AssetService } from 'src/app/core/asset.service';
import { Asset } from 'src/app/core/asset.model';

@Component({
  selector: 'cc-setting',
  templateUrl: './setting.component.html',
})
export class SettingComponent implements OnInit {

  form!: FormGroup;

  assets: Asset[] = [];

  constructor(private formBuilder: FormBuilder, private assetService: AssetService, public settingService: SettingService) { }

  ngOnInit(): void {
    this.assetService.getAssets().subscribe(response => this.assets = response);
    this.settingService.get().subscribe(setting => {
      this.form = this.formBuilder.group({
        id: [setting.id ?? 1],
        currency: [setting.currency, [Validators.required]],
        ticker: [setting.ticker, [Validators.required]],
        fav1: [setting.fav1, [Validators.required]],
        fav2: [setting.fav2, [Validators.required]],
        fav3: [setting.fav3, [Validators.required]],
        fav4: [setting.fav4, [Validators.required]],
      });
    });
  }

  save(): void {
    this.settingService.save(this.form.value).subscribe();
  }
}
