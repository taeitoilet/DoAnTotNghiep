import { Injectable } from '@angular/core';
import {HttpBaseService} from "../http-base-service/http-base.service";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment.development";
import {jwtDecode} from "jwt-decode";
import {LocalStorageService} from "../local-storage.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  getUserId(token: string): any {
    const decodedToken = this.getDecodedToken(token);

    if (!decodedToken || !decodedToken.userId)
      return ''
    else
      localStorage.setItem('userId', decodedToken.userId);
  }

  constructor(private httpBaseService: HttpBaseService,
              private LocalStorageService: LocalStorageService) { }

  getDecodedToken(token: string): any {
    try {
      if (!token) return null;
      return jwtDecode(token);
    } catch (error) {
      console.error('Invalid token: ', error);
      return null;
    }
  }

  getRoles(): string[] {
    try {
      const token = this.LocalStorageService.getToken();
      if (!token) {
        console.log("Token: ", token)
        return []
      }

      const decodedToken = this.getDecodedToken(token);
      if (!decodedToken || !decodedToken.scope) return [];

      console.log("Token: ", decodedToken.scope)

      return decodedToken.scope.split(' ');
    } catch (error) {
      console.error('Error getting roles: ', error);
      return [];
    }
  }

  hasRole(role: string): boolean {
    if (!role) return false;
    return this.getRoles().includes(role);
  }

  login(username: string, password: string): Observable<any> {
    return this.httpBaseService.post(environment.httpBaseUrl + 'auth/log-in', {username, password});
  }

  refreshToken(body: { token: string }): Observable<any> {
    return this.httpBaseService.post(environment.httpBaseUrl + 'auth/refresh', body);
  }

  logout(token: any): Observable<any> {
    return this.httpBaseService.post(environment.httpBaseUrl + 'auth/logout', token);
  }
}
