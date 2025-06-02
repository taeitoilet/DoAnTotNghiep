export class dish{
  dishId : string;
  dishName : string;
  dishDescription : string;
  dishPriceValue :number;
  dishStatus : string;
  dishImageUrl : string;
  dishTypeName : string;
  restaurant : string;
  dishSalePrice : number;
  restaurantId : number
  constructor() {
    this.dishId = '';
    this.dishName = ''
    this.dishDescription = ''
    this.dishPriceValue = 0
    this.dishStatus = ''
    this.dishImageUrl = ''
    this.dishTypeName = ''
    this.restaurant = ''
    this.dishSalePrice = 0
    this.restaurantId  = 0
  }

}
export class dishType{
  dishTypeId : number | null = null;
  dishTypeName : string | null = null;
  dishTypeDescription : string | null = null;
  dishTypeCreatedAt : string | null = null;
  dishTypeCreatedBy : string | null = null;
  dishTypeUpdatedAt : string | null = null;
  dishTypeUpdatedBy : string | null = null;
}
