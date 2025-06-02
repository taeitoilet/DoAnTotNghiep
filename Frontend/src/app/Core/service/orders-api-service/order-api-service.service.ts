import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {createOrder, updateOrder} from "../model/orders";
import {Page} from "../model/page";

@Injectable({
  providedIn: 'root'
})
export class OrderApiServiceService {

  constructor(private http: HttpClient) { }

  createOrder(request : createOrder){
    const url = this.addDynamicURL('http://localhost:8080/foodee/food-order/order/create/v1.0');
    return this.http.post(
      url,request
    );
  }
  getAllOrder(page : Page){
    const url = this.addDynamicURL('http://localhost:8080/foodee/food-order/order/list/v1.0',{page});
    return this.http.post(
      url,{page}
    );
  }
  getAllOrderByUser(page : Page){
    const url = this.addDynamicURL('http://localhost:8080/foodee/food-order/order/user-order/v1.0',{page});
    return this.http.post(
      url,{page}
    );
  }
  getOrderByUser(page : Page){
    const url = this.addDynamicURL('http://localhost:8080/foodee/food-order/order/pending-user-order/v1.0',{page});
    return this.http.post(
      url,{page}
    );
  }
  getPendingOrder(page : Page){
    const url = this.addDynamicURL('http://localhost:8080/foodee/food-order/order/list-pending/v1.0',{page});
    return this.http.post(
      url,{page}
    );
  }
  updateOrder(request : updateOrder) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/food-order/order/update/v1.0');
    return this.http.post(
      url,request
    );
  }
  getDetailOrder(id : string){
    const url = this.addDynamicURL('http://localhost:8080/foodee/food-order/order/detail/v1.0');
    return this.http.post(
      url,{id}
    );
  }
  deleteOrder(orderId : number){
    const url = this.addDynamicURL('http://localhost:8080/foodee/food-order/order/delete/v1.0');
    return this.http.post(
      url,{orderId}
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
