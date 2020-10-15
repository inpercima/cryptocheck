import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { SettingsService } from './settings.service';

@Component({
  selector: 'cc-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {

  settingsForm: FormGroup;

  constructor(private formBuilder: FormBuilder, public settingsService: SettingsService) { }

  ngOnInit(): void {
    this.settingsService.get().subscribe(settings => {
      this.settingsForm = this.formBuilder.group({
        currency: [settings.currency, [Validators.required]],
        ticker: [settings.ticker, [Validators.required]],
        fav1: [settings.fav1, [Validators.required]],
        fav2: [settings.fav2, [Validators.required]],
        fav3: [settings.fav3, [Validators.required]],
        fav4: [settings.fav4, [Validators.required]],
      });
    });
  }

  save(): void {
    this.settingsService.save(this.settingsForm.value).subscribe();
  }
}
