import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ReportApiServiceService {

  constructor(private http: HttpClient) { }
  getDailyReport(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/daily/v1.0');
    return this.http.post(
      url,null
    );
  }
  getMonthlyReport(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/monthly/v1.0');
    return this.http.post(
      url,null
    );
  }
  getWeeklyReport(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/weekly/v1.0');
    return this.http.post(
      url,null
    );
  }
  getDailySale(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/daily-sale/v1.0');
    return this.http.post(
      url,null
    );
  }
  getMonthlySale(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/monthly-sale/v1.0');
    return this.http.post(
      url,null
    );
  }
  getWeeklySale(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/weekly-sale/v1.0');
    return this.http.post(
      url,null
    );
  }
  getYearlySale(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/yearly-sale/v1.0');
    return this.http.post(
      url,null
    );
  }
  getYearlyOrder(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/yearly-order/v1.0');
    return this.http.post(
      url,null
    );
  }
  getMonthlyOrder(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/monthly-order/v1.0');
    return this.http.post(
      url,null
    );
  }
  getWeeklyOrder(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/weekly-order/v1.0');
    return this.http.post(
      url,null
    );
  }
  getDailyOrder(){
    const url = this.addDynamicURL('http://localhost:8080/foodee/report/daily-order/v1.0');
    return this.http.post(
      url,null
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
