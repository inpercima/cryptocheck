import { Injectable } from '@angular/core';

import { of, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SyncService {

  constructor() { }

  sync(): Observable<number> {
    return of(0);
  }
}
