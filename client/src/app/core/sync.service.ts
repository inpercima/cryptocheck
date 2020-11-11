import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SyncService {

  constructor(private http: HttpClient) { }

  sync(): Observable<string> {
    return this.http.get<string>(environment.api + 'synchronize');
  }
}
