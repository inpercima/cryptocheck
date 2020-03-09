import { Injectable } from '@angular/core';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  public url(url: string, parameters?: string): string {
    if (!parameters) {
      parameters = '';
    }
    return `${environment.api}${url}${environment.apiSuffix}${parameters}`;
  }
}
