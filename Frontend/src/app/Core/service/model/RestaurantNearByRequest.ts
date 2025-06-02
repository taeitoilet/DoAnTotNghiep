import {Page} from "./page";

export class RestaurantNearByRequest{
  page : Page;
  latitude : string | null;
  longitude : string | null;

  constructor() {
    this.page = new Page();
    this.latitude = '21.0492744';
    this.longitude = '105.7324997';
  }

}
