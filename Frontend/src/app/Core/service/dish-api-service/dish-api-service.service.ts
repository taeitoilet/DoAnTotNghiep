import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GetResDishRequest, QueryDishRequest} from "../model/dishRequests";
import {Page} from "../model/page";
import {RestaurantDishRequest} from "../model/RestaurantDishRequest";

@Injectable({
  providedIn: 'root'
})
export class DishApiServiceService {

  constructor(private http: HttpClient) { }


  getRestaurantDishes(request: GetResDishRequest) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/dish/res-dishes/v1.0');
    return this.http.post(
      url,request
    );
  }
  getRestaurantDishesV2(request: GetResDishRequest) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/dish/res-dishes/v2.0');
    return this.http.post(
      url,request
    );
  }
  getDetailDishes(dishId : string) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/dish/detail/v1.0', { dishId });
    return this.http.post(
      url, {dishId}
    );
  }
  getListDishes(request : QueryDishRequest) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/dish/list/v1.0');
    return this.http.post(
      url, request
    );
  }
  getCategoryDish(page: Page = new Page()) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/sys-management/dish-type/list/v1.0',{ page });
    return this.http.post(
      url, { page }
    );
  }

  getDishDetail(dishId : string) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/dish/detail/v1.0', { dishId });
    return this.http.post(
      url, {dishId}
    );
  }

  deleteDish(dishId : number[]) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/dish/delete/v1.0', { dishId });
    return this.http.post(
      url, {dishId}
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
