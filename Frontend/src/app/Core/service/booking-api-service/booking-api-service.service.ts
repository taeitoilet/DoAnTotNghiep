import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {answerBookingRequest, createBookingRequest, queryBookingRequest} from "../model/booking";

@Injectable({
  providedIn: 'root'
})
export class BookingApiServiceService {

  constructor(private http: HttpClient) { }

  createBooking(request : createBookingRequest){
    const url = this.addDynamicURL('http://localhost:8080/foodee/booking/create/v1.0');
    return this.http.post(
      url, request
    );
  }
  getAllBooking(request : queryBookingRequest){
    const url = this.addDynamicURL('http://localhost:8080/foodee/booking/list/v1.0');
    return this.http.post(
      url, request
    );
  }
  getPendingBooking(request : queryBookingRequest){
    const url = this.addDynamicURL('http://localhost:8080/foodee/booking/pending-list/v1.0');
    return this.http.post(
      url, request
    );
  }
  answerBooking(request : answerBookingRequest){
    const url = this.addDynamicURL('http://localhost:8080/foodee/booking/status/v1.0');
    return this.http.post(
      url, request
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
}
