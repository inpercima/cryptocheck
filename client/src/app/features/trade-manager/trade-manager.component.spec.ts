import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeManagerComponent } from './trade-manager.component';

describe('TradeManagerComponent', () => {
  let component: TradeManagerComponent;
  let fixture: ComponentFixture<TradeManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TradeManagerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TradeManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
