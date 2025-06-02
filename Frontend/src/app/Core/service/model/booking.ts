import {Page} from "./page";

export class createBookingRequest {
  bookingDate : string = ''
  bookingTime : string = ''
  bookingNumberOfPeople : number = 0
  bookingNote : string = ''
  bookingUserPhone : string = ''
  bookingUserName : string = ''
  restaurantId : number = 0
}
export class queryBookingRequest{
  page : Page = new Page();
  bookingDate : string | null = null
  bookingTime : string  | null = null
  bookingPhone : string  | null = null
}
export class answerBookingRequest{
  bookingId : number = 0
  bookingStatus : string = ''
}
export class booking{
  bookingId : string = ''
  bookingDate : string = ''
  bookingTime : string = ''
  bookingNumberOfPeople : number = 0
  bookingNote : string = ''
  bookingStatus : string = ''
  bookingPhone : string = ''
  restaurantName : string = ''
  bookingUserName : string = ''
  tableName : string = ''
}
