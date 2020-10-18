import { TestBed } from '@angular/core/testing';

import { SyncDialogService } from './sync-dialog.service';

describe('SyncDialogService', () => {
  let service: SyncDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SyncDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
