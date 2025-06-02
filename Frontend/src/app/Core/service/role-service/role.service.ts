import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) { }

  getRoles() {
    return this.http.post("http://localhost:8080/foodee/authorization/auth/role/list/v1.0", {responseType: 'json'});
  }
}
