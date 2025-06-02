import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Page} from "../model/page";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: `Bearer ${sessionStorage.getItem('token')}`,
  }),
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUserList(page: Page){
    const url = 'http://localhost:8080/foodee/authorization/auth/user/list/v1.0';
    return this.http.post(url, {page});
  }

  getMyInfo(){
    const url = 'http://localhost:8080/foodee/authorization/auth/user/my-info/v1.0';
    return this.http.post(url, {});
  }

  getUserDetail(id: string){
    const url = 'http://localhost:8080/foodee/authorization/auth/user/detail/v1.0';
    return this.http.post(url, {id});
  }

  createUser(req: {
    userName: any;
    userFullName: any;
    userEmail: any;
    userPhone: any;
    gender: any;
    userStatus: any;
    userAvatarUrl: any
  }){
    const url = 'http://localhost:8080/foodee/authorization/auth/user/create/v1.0';
    return this.http.post(url, req);
  }

  updateUser(req: {
    userId: string | undefined;
    username: string | undefined;
    userFullName: any;
    userEmail: any;
    userPhone: any;
    gender: string
  }){
    const url = 'http://localhost:8080/foodee/authorization/auth/user/update/v1.0';
    return this.http.post(url, req);
  }

  updateUserRole(req: {userId: string, roleName: string[]}){
    const url = 'http://localhost:8080/foodee/authorization/auth/user/update/role/v1.0';
    return this.http.post(url, req);
  }

  updateUserStatus(req: {userId: string, userStatus: string}){
    const url = 'http://localhost:8080/foodee/authorization/auth/user/update/status/v1.0';
    return this.http.post(url, req);
  }

  deleteUser(req: {userId: string[]}){
    const url = 'http://localhost:8080/foodee/authorization/auth/user/delete/v1.0';
    return this.http.post(url, req);
  }
}
