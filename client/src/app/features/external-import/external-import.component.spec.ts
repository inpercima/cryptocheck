import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExternalImportComponent } from './external-import.component';

describe('ExternalImportComponent', () => {
  let component: ExternalImportComponent;
  let fixture: ComponentFixture<ExternalImportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExternalImportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExternalImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
