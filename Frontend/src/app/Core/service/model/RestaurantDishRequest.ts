import {Page} from "./page";

export class RestaurantDishRequest {
    page: Page = new Page;
    restaurantId : number = 0;
    dishTypeId: number | null = null;
}
