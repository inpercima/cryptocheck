import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CryptoPriceComponent } from './crypto-price.component';
import { CryptoPrice } from './crypto-price.model';

describe('CryptoPriceComponent', () => {
  let component: CryptoPriceComponent;
  let fixture: ComponentFixture<CryptoPriceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CryptoPriceComponent ],
      imports: [
        BrowserAnimationsModule,
        HttpClientTestingModule,
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CryptoPriceComponent);
    component = fixture.componentInstance;
    component.price = { coin: 'ETH', currency: 'EUR', value: 230.05, trend: 'NORMAL', locale: 'de-DE' } as CryptoPrice;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
