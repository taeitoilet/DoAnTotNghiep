export class Restaurant {
  restaurantId : number;
  restaurantName : string;
  restaurantAddress : string;
  restaurantPhone : string;
  restaurantAvgRatingText : string;
  restaurantPhotoUrl : string;
  restaurantLatitude : string;
  restaurantLongitude : string;
  categoryName : [] | string[];
  restaurantDescription : string;
  restaurantStatus : string;
  restaurantIsOpening : boolean;
  restaurantIsAds : boolean;
  restaurantIsBooking : boolean;
  restaurantIsDelivery : boolean;
  userId : string;
  distance : number;

  constructor() {
    this.categoryName = [];
    this.restaurantId = 0
    this.restaurantName = ''
    this.restaurantAddress = ''
    this.restaurantPhone = ''
    this.restaurantAvgRatingText = ''
    this.restaurantPhotoUrl = ''
    this.restaurantLatitude = ''
    this.restaurantLongitude = ''
    this.restaurantDescription = ''
    this.restaurantStatus = ''
    this.restaurantIsOpening = false;
    this.restaurantIsAds = false;
    this.restaurantIsBooking = false;
    this.restaurantIsDelivery = false;
    this.userId = ''
    this.distance = 0
  }

}
