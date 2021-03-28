import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  assets: string[] = [];

  constructor(private http: HttpClient) {
    this.getAssets().subscribe(response => this.assets = response);
   }

  getAssets(): Observable<string[]> {
    return this.http.get<string[]>(environment.api + 'asset');
  }
}
