import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoinPriceComponent } from './coin-price.component';

describe('CoinPriceComponent', () => {
  let component: CoinPriceComponent;
  let fixture: ComponentFixture<CoinPriceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CoinPriceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CoinPriceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
