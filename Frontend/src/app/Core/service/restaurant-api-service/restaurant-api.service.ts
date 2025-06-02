import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Page} from "../model/page";
import {RestaurantNearByRequest} from "../model/RestaurantNearByRequest";
import {RestaurantQueryRequest} from "../model/RestaurantQueryRequest";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: `Bearer ${sessionStorage.getItem('token')}`,
  }),
};

@Injectable({
  providedIn: 'root',
})
export class RestaurantApiService {

  constructor(private http: HttpClient) { }

  getRestaurantList(page: Page) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/sys-management/restaurant/list/v1.0', { page });
    return this.http.post(
      url, {page}
    );
  }

  getRestaurantDetail(restaurantId: number) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/sys-management/restaurant/detail/v1.0');
    return this.http.post(
      url, {restaurantId}
    );
  }
  getActiveRestaurant(request: RestaurantQueryRequest){
    const url = this.addDynamicURL('http://localhost:8080/foodee/sys-management/restaurant/active-res/v1.0');
    return this.http.post(
      url,request
    );
  }
  getPendingRestaurantList(page: Page, queryParams?: any) {
    const url = this.addDynamicURL('http://localhost:8080/foodee/sys-management/restaurant/pending-res/v1.0', { page });
    return this.http.post(
      url, {page}
    );
  }

  getNearbyRestaurant(request : RestaurantNearByRequest){
    const url = this.addDynamicURL('http://localhost:8080/foodee/sys-management/restaurant/near-by/v1.0', { request });
    return this.http.post(
      url , request);
  }
  findRestaurant(request : RestaurantQueryRequest){
    const url = this.addDynamicURL('http://localhost:8080/foodee/sys-management/restaurant/active-res/v1.0', { request });
    return this.http.post(
      url , request);
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
