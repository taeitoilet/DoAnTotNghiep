import {Component, OnInit} from '@angular/core';
import { PageTitleComponent } from '../../../../../layouts/page-title/page-title.component';
import { LucideAngularModule } from 'lucide-angular';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {orders, updateOrder} from "../../../../../Core/service/model/orders";
import {OrderApiServiceService} from "../../../../../Core/service/orders-api-service/order-api-service.service";
import {DatePipe, DecimalPipe, NgForOf, NgIf} from "@angular/common";
import {AuthService} from "../../../../../service/auth-service/auth.service";
import {PopupNotiService} from "../../../../../Core/service/popup-noti-service/popup-noti.service";

@Component({
    selector: 'app-apps-ecommerce-orders-overview',
  imports: [PageTitleComponent, LucideAngularModule, RouterLink, DatePipe, NgIf, NgForOf, DecimalPipe],
    templateUrl: './apps-ecommerce-orders-overview.component.html',
    styleUrl: './apps-ecommerce-orders-overview.component.scss'
})
export class AppsEcommerceOrdersOverviewComponent implements OnInit {
  order : orders = new orders()
  orderId : string = '28'
  subTotal : number = 0
  vat : number = 0
  discount : number = 0
  shipFee : number = 0
  totalAmount : number = 0
  role : string[] = []
  showConfirmModal = false;
  updateOrderRequest : updateOrder = new updateOrder()
  constructor(private activatedRoute: ActivatedRoute,private popup: PopupNotiService, private authService: AuthService,public orderApiService : OrderApiServiceService,public router: Router) {
  }
  getDetailOrder(){
    this.orderApiService.getDetailOrder(this.orderId).subscribe((data: any) => {
      this.order = data.data;
      if(this.order){
        for (let item of this.order.orderItems){
          this.subTotal += item.orderItemPrice * item.orderItemQuantity
        }
        this.shipFee = 30000
        this.vat = this.subTotal * 0.06
        this.totalAmount = this.subTotal + this.vat + this.shipFee
      }

    })
  }

  ngOnInit(): void {
    this.orderId = Number(this.activatedRoute.snapshot.paramMap.get('id')).toString();
    this.getDetailOrder();
    this.role = this.authService.getRoles()
  }
  updateOrder(orderId : number, status : string){
    this.updateOrderRequest.orderId = orderId;
    this.updateOrderRequest.orderStatus = status;
    this.orderApiService.updateOrder(this.updateOrderRequest).subscribe((data : any) => {
      this.order = data.data;
      this.getDetailOrder();
    })

  }
  cancelOrder(orderId : number){
    this.orderApiService.deleteOrder(orderId).subscribe((data : any) => {
      this.order = data.data;
      this.popup.success("Đơn hàng đã được hủy thành công");
      this.showConfirmModal = false;
      this.router.navigate(['/dashboard/apps-ecommerce-orders-list']);
    })
  }
  openConfirmModal() {
        this.showConfirmModal = true;
  }
  cancelConfirmModal(){
    this.showConfirmModal = false;
  }
}
