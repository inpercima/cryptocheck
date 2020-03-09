import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { CryptoService } from './crypto.service';

describe('CryptoService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [ HttpClientTestingModule ],
  }));

  it('should be created', () => {
    const service: CryptoService = TestBed.inject(CryptoService);
    expect(service).toBeTruthy();
  });
});
