import {Page} from "./page";

export class QueryDishRequest {
  page : Page = new Page();
  restaurantName : string | null;
  createdDateFrom : string | null;
  createdDateTo : string | null;
  dishType : string | null;
  dishName : string | null;
  amountFrom : number | null;
  amountTo : number | null;
  constructor() {
    this.page = new Page();
    this.restaurantName = null
    this.createdDateFrom =  null
    this.createdDateTo =  null
    this.dishType =  null
    this.dishName =  null
    this.amountFrom  =  null
    this.amountTo =  null
  }
}
export class GetResDishRequest{
  page: Page = new Page();
  restaurantId : number = 0;
}
