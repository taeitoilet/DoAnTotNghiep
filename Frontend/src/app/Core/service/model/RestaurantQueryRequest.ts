import {Page} from "./page";

export class RestaurantQueryRequest {
  page: Page;
  restaurantName : string | null;
  restaurantAddress : string | null;
  restaurantPhone : string | null;
  categoryId : number | null;
  restaurantStatus : number | null;
  ownerName : string | null;
  constructor() {
    this.page = new Page();
    this.restaurantName = null;
    this.restaurantAddress = null;
    this.restaurantPhone = null;
    this.restaurantStatus = null;
    this.categoryId  = null;
    this.restaurantStatus = null;
    this.ownerName = null;
  }
}
