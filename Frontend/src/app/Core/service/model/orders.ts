export class orders{
  orderId : number = 0
  orderTotalAmount : number = 0
  orderStatus : string = ''
  orderPaymentMethod : string = ''
  orderPaymentStatus : string = ''
  orderDeliveryAddress : string = ''
  orderCreatedAt : string = ''
  orderCreatedBy : string = ''
  orderUpdatedAt : string = ''
  orderUpdatedBy : string = ''
  orderItems : oderItems[] = []
  userId : string = ''
  restaurantId : string = ''
  userPhone : string = ''
  userName : string = ''
}

export class oderItems{
  orderItemId : number = 0
  orderId : number = 0
  dishId : number = 0
  dishName : string = ''
  orderItemQuantity : number = 0
  orderItemPrice : number = 0
  orderItemNote : string = ''
  dishPhotoUrl : string = ''
}

export  class createOrder{
  orderPaymentMethod : string = ''
  orderPaymentStatus : string = ''
  orderDeliveryAddress : string = ''
  restaurantId : number = 0
  userId : string = ''
  userPhone : string = ''
  userName : string = ''
  orderTotalAmount : number = 0
  orderItems : createOrderItemRequest[] = []
}
export class createOrderItemRequest{
  dishId : number = 0
  orderItemPrice : number = 0
  orderItemQuantity : number = 0
  orderItemNote : string = ''
}
export class updateOrder{
  orderId : number = 0
  orderStatus : string = ''
}
