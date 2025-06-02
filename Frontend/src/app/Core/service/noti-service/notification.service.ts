import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient) { }

  markAsRead(notificationIds: number[]){
    const url = 'http://localhost:8080/foodee/notify/markAsRead/v1.0';
    return this.http.post(url, {notificationIds});
  }
}
