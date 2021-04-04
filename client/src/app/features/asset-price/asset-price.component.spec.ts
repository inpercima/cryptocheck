import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssetPriceComponent } from './asset-price.component';

describe('AssetPriceComponent', () => {
  let component: AssetPriceComponent;
  let fixture: ComponentFixture<AssetPriceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssetPriceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssetPriceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
