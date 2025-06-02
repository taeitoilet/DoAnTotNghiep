export class DailyReport{
  timeRange:string = "00:00";
  totalRevenue : number = 0;
  totalQuantity : number = 0;
  netRevenue : number = 0;
}
export class WeeklyReport{
  date:string = "";
  totalRevenue : number = 0;
  totalQuantity : number = 0;
  netRevenue : number = 0;
}
export class MonthlyReport{
  monthNumber:number = 0;
  totalRevenue : number = 0;
  totalQuantity : number = 0;
  netRevenue : number = 0;
}
export class DishSale{
  dishName:string = '';
  totalRevenue : number = 0;
  totalQuantity : number = 0;
  imageUrl : string = '';
}
