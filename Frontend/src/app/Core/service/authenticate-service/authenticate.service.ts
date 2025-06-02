import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Page} from "../model/page";
import {UserRegisRequest} from "../model/UserRegisRequest";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: `Bearer ${sessionStorage.getItem('token')}`,
  }),
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  callbackUrl = "https://accounts.google.com/o/oauth2/auth";
  authUrl = "http://localhost:4200";
  googleClientId = "704224005006-99quqcrndf1jj5jl4rl8umc0ebrg5l9d.apps.googleusercontent.com";

  constructor(private http: HttpClient) { }

  targetUrl = `${this.authUrl}?redirect_uri=${encodeURIComponent(
    this.callbackUrl
  )}&response_type=code&client_id=${this.googleClientId}&scope=openid%20email%20profile`;

  signup(userRegisRequest: UserRegisRequest) {
    return this.http.post(
      'http://localhost:8080/foodee/authorization/auth/user/register/v1.0',
       userRegisRequest
    );
  }

  login(username: string, password: string) {
    return this.http.post(
      'http://localhost:8080/foodee/auth/token',
      {username, password}
    );
  }

  logout() {
    return this.http.post(
      'http://localhost:8080/foodee/auth/logout',
      { token: localStorage.getItem('token') }
    );
  }

  addDynamicURL(url: string, stringParams?: any) {
    if (stringParams) {
      Object.keys(stringParams).forEach((key) => {
        url = url.replace('{' + key + '}', stringParams[key]);
      });
    }
    return url;
  }

  addQueryParams(params?: any) {
    let searchParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      searchParams = searchParams.append(key, params[key]);
    });
    return { params: searchParams };
  }
}
