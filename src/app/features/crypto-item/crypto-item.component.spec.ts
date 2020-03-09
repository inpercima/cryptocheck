import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CryptoItemComponent } from './crypto-item.component';
import { CryptoItem } from './crypto-item.model';

describe('CryptoItemComponent', () => {
  let component: CryptoItemComponent;
  let fixture: ComponentFixture<CryptoItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CryptoItemComponent ],
      imports: [ HttpClientTestingModule ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CryptoItemComponent);
    component = fixture.componentInstance;
    component.currency = 'EUR';
    component.item = { id: 1, coin: 'ETH', amount: 2.345, purchasePrice: 220.34 } as CryptoItem;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
