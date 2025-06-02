import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HttpBaseService {

  constructor(private http: HttpClient) { }

  private createHeaders(customHeaders?: {[key: string]: string}): HttpHeaders {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    if (customHeaders) {
      for (const key in customHeaders) {
        if (customHeaders.hasOwnProperty(key)) {
          headers = headers.set(key, customHeaders[key]);
        }
      }
    }
    return headers;
  }

  private createParams(params?: {[key: string]: string}): HttpParams{
    let httpParams = new HttpParams();
    if (params) {
      for (const key in params) {
        if (params.hasOwnProperty(key)) {
          httpParams = httpParams.set(key, params[key]);
        }
      }
    }

    return httpParams;
  }

  get<T>(url: string, params?: {[key: string]: string}, customHeaders?: {[key: string]: string}): Observable<T>{
    const options ={
      headers: this.createHeaders(customHeaders),
      params: this.createParams(params)
    };
    return this.http.get<T>(url, options);
  }

  post<T>(url: string, body: any, params?: {[key: string]: string}, customHeaders?: {[key: string]: string}): Observable<T>{
    const options = {
      headers: this.createHeaders(customHeaders),
      params: this.createParams(params)
    };
    return this.http.post<T>(url, body, options);
  }

  put<T>(url: string, body: any, params?: {[key: string]: string}, customHeaders?: {[key: string]: string}): Observable<T>{
    const options = {
      headers: this.createHeaders(customHeaders),
      params: this.createParams(params)
    };
    return this.http.put<T>(url, body, options);
  }

  delete<T>(url: string, params?: {[key: string]: string}, customHeaders?: {[key: string]: string}): Observable<T>{
    const options = {
      headers: this.createHeaders(customHeaders),
      params: this.createParams(params)
    };
    return this.http.delete<T>(url, options);
  }
}
