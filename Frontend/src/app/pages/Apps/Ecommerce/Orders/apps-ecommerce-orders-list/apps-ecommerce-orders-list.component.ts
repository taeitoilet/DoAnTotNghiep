import { Component } from '@angular/core';
import { PageTitleComponent } from '../../../../../layouts/page-title/page-title.component';
import { ColDefs, DomixGridTestComponent } from '../../../../../componate/domix-grid-test/domix-grid-test.component';
import { CommonModule } from '@angular/common';
import { DomixTableService } from '../../../../../componate/domix-grid-test/service/domix-table.service';
import { RestApiService } from '../../../../../Core/service/rest-api.service';
import { DomixDropdownModule } from '../../../../../module/domix dropdown/domix-dropdown.module';
import { DomixPaginationComponent } from '../../../../../componate/domix-pagination/domix-pagination.component';
import { FormsModule } from '@angular/forms';
import { LucideAngularModule } from 'lucide-angular';
import { ModalService } from '../../../../../Core/service/modal/modal.service';
import {OrderApiServiceService} from "../../../../../Core/service/orders-api-service/order-api-service.service";
import {Page} from "../../../../../Core/service/model/page";
import {orders, updateOrder} from "../../../../../Core/service/model/orders";
import {Router} from "@angular/router";
import {AuthService} from "../../../../../service/auth-service/auth.service";
import {PopupNotiService} from "../../../../../Core/service/popup-noti-service/popup-noti.service";

export enum Payment {
  COD,
  Unpaid,
  Paid,
}

export enum OrderStatusList {
  Delivered,
  Shipping,
  Paid,
  New,
  Pending,
  Cancelled,
  AllOrder
}

export interface OrderList {
  ordersDate: string,
  deliveredDate: string,
  customersName: string,
  productName: string,
  payment: number,
  price: string,
  total: string,
  qty: number,
  status: number,
  image: string,
  orderID: string,
}
@Component({
    selector: 'app-apps-ecommerce-orders-list',
    imports: [PageTitleComponent, CommonModule, DomixDropdownModule, DomixPaginationComponent, FormsModule, LucideAngularModule],
    templateUrl: './apps-ecommerce-orders-list.component.html',
    styleUrl: './apps-ecommerce-orders-list.component.scss'
})

export class AppsEcommerceOrdersListComponent extends DomixGridTestComponent {
  page : Page = new Page()
  updateOrderRequest : updateOrder = new updateOrder()
  role : string[] = []
  pageNum : number = 1;
  defs: ColDefs[] = [
    {
      headerName: 'Mã đơn hàng',
      field: 'orderId',
      sortable: true,
      sortDiraction: 'asc'
    },
    {
      headerName: 'Ngày nhận',
      field: 'orderCreatedAt',
      sortable: true,
      sortDiraction: 'asc'
    },
    {
      headerName: 'Khách hàng',
      field: 'userName',
      sortable: true,
      sortDiraction: 'asc'
    },
    {
      headerName: 'SĐT',
      field: 'userPhone',
      sortable: true,
      sortDiraction: 'asc'
    },
    {
      headerName: 'Số lượng món',
      field: 'orderItems',
      sortable: true,
      sortDiraction: 'asc'
    },

    {
      headerName: 'Tổng tiền',
      field: 'orderTotalAmount',
      sortable: true,
      sortDiraction: 'asc'
    },
    {
      headerName: 'Trạng thái',
      field: 'orderStatus',
      sortable: true,
      sortDiraction: 'asc'
    },
  ]

  selectCategory : string = 'Pending'
  Payment = Payment;
  orders : orders = new orders()
  constructor(public tableS: DomixTableService, private authService: AuthService,private popup: PopupNotiService,private apiService: RestApiService, private modalSer: ModalService, public orderApiService : OrderApiServiceService,private router: Router) { super(tableS) }

  summary = [
    { title: 'Đơn mới', count: 345, percentage: '19%', class: 'bg-primary-100 dark:bg-primary-500/10 border-primary-200 dark:border-primary-500/20', icon: 'ri-arrow-right-up-line' },
    { title: 'Đang chờ', count: 120, percentage: '2.98%', class: 'bg-yellow-100 dark:bg-yellow-500/10 border-yellow-200 dark:border-yellow-500/20', icon: 'ri-arrow-right-down-line' },
    { title: 'Thành công', count: 225, percentage: '8.56%', class: 'bg-green-100 dark:bg-green-500/10 border-green-200 dark:border-green-500/20', icon: 'ri-arrow-right-up-line' },
    { title: 'Tổng đơn', count: 9451, percentage: '24.08%', class: 'bg-purple-100 dark:bg-purple-500/10 border-purple-200 dark:border-purple-500/20', icon: 'ri-arrow-right-up-line' }
  ]
  ngOnInit() {
    this.role = this.authService.getRoles()
  }
  getSelectedData() {
    if (this.selectCategory === 'All' ) {
      if (this.role.includes('ROLE_RESTAURANT')){
        this.orderApiService.getAllOrder(this.page).subscribe((data : any) => {
          this.gridData = data.data.items;
          this.displayedData = [...this.gridData];
          this.totalItems = data.data.total;
          this.pageSize = this.page.pageSize
          this.currentPage = this.page.pageNum
          this.updateDisplayedData();
        })
      }
      if (this.role.includes('ROLE_USER')){
        this.orderApiService.getAllOrderByUser(this.page).subscribe((data : any) => {
          this.gridData = data.data.items;
          this.displayedData = [...this.gridData];
          this.totalItems = data.data.total;
          this.pageSize = this.page.pageSize
          this.currentPage = this.page.pageNum
          this.updateDisplayedData();
        })
      }
    } else {
      if (this.role.includes('ROLE_RESTAURANT')){
        this.orderApiService.getPendingOrder(this.page).subscribe((data : any) => {
          this.gridData = data.data.items;
          this.displayedData = [...this.gridData];
          this.totalItems = data.data.total;
          this.pageSize = this.page.pageSize
          this.currentPage = this.page.pageNum
          this.updateDisplayedData();
        })
      }
      if (this.role.includes('ROLE_USER')){
        this.orderApiService.getOrderByUser(this.page).subscribe((data : any) => {
          this.gridData = data.data.items;
          this.displayedData = [...this.gridData];
          this.totalItems = data.data.total;
          this.pageSize = this.page.pageSize
          this.currentPage = this.page.pageNum
          this.updateDisplayedData();
        })
      }
    }
  }
  override onPageChange(newPage: number) {
    this.page.pageNum = newPage;
    this.getSelectedData()
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  override nextPage(): void {
    this.page.pageNum++;
    this.getSelectedData()
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  override prevPage(): void {
    if (this.page.pageNum > 1) {
      this.page.pageNum--;
      this.getSelectedData()
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }

  override goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      window.scrollTo({ top: 0, behavior: 'smooth' });
      this.page.pageNum = page;
      this.getSelectedData()
    }
  }

  updateOrder(orderId : number, status : string){
    this.updateOrderRequest.orderId = orderId;
    this.updateOrderRequest.orderStatus = status;
    this.orderApiService.updateOrder(this.updateOrderRequest).subscribe((data : any) => {
        this.orders = data.data;
        this.getSelectedData()
    })
  }
  cancelOrder(orderId : number){
    this.orderApiService.deleteOrder(orderId).subscribe((data : any) => {
      this.orders = data.data;
      this.popup.success("Đơn hàng đã được hủy thành công");
      this.getSelectedData()
    })
  }
  goToOrderDetail(id:number): void {
    this.router.navigate(['dashboard/apps-ecommerce-orders-overview',id]);
  }

  onSelectCategory(category: string) {
    if (this.selectCategory !== category) {
      this.selectCategory = category;
      this.getSelectedData();
    }
  }
}
