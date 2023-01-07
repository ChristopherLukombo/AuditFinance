import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Action } from 'src/app/models/action';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ActionService {
  private readonly resourceUrl = environment.serverUrl;

  constructor(private http: HttpClient) {}

  consultation(
    stockKey: string,
    date: string
  ): Observable<HttpResponse<Action>> {
    return this.http.get<Action>(
      `${this.resourceUrl}/consultation?stockKey=${stockKey}&date=${date}`,
      {
        observe: 'response'
      }
    );
  }

  consultationWeb(
    stockKey: string,
    dateFrom: string,
    dateTo: string
  ): Observable<HttpResponse<Action>> {
    return this.http.get<Action>(
      `${this.resourceUrl}/consultation/web?stockKey=${stockKey}&dateFrom=${dateFrom}&dateTo=${dateTo}`,
      {
        observe: 'response'
      }
    );
  }

  consultMostWanted(): Observable<HttpResponse<History>> {
    return this.http.get<History>(
      `${this.resourceUrl}/consultation/mostWanted`,
      {
        observe: 'response'
      }
    );
  }
}
