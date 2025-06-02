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
import {BookingApiServiceService} from "../../../../../Core/service/booking-api-service/booking-api-service.service";
import {answerBookingRequest, queryBookingRequest} from "../../../../../Core/service/model/booking";

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
    templateUrl: './apps-ecommerce-booking-list.component.html',
    styleUrl: './apps-ecommerce-booking-list.component.scss'
})

export class AppsEcommerceBookingListComponent extends DomixGridTestComponent {
  page : Page = new Page()
  queryBookingRequest : queryBookingRequest = new queryBookingRequest()
  answerBooking : answerBookingRequest = new answerBookingRequest()

  pageNum : number = 1;
  defs: ColDefs[] = [
    {
      headerName: 'Mã đặt bàn',
      field: 'bookingId',
      sortable: false,
      sortDiraction: 'asc'
    },
    {
      headerName: 'Ngày nhận',
      field: 'bookingDate',
      sortable: false,
      sortDiraction: 'asc'
    },
    {
      headerName: 'Thời gian',
      field: 'bookingTime',
      sortable: false,
      sortDiraction: 'asc'
    },
    {
      headerName: 'Khách hàng',
      field: 'bookingUserName',
      sortable: false,
      sortDiraction: 'asc'
    },
    {
      headerName: 'SĐT',
      field: 'bookingPhone',
      sortable: false,
      sortDiraction: 'asc'
    },
    {
      headerName: 'Số khách',
      field: 'bookingNumberOfPeople',
      sortable: false,
      sortDiraction: 'asc'
    },
    {
      headerName: 'Trạng thái',
      field: 'bookingStatus',
      sortable: false,
      sortDiraction: 'asc'
    },
  ]

  selectCategory : string = 'Pending'
  Payment = Payment;
  orders : orders = new orders()
  constructor(public tableS: DomixTableService, private apiService: RestApiService, private modalSer: ModalService, public bookingApiService : BookingApiServiceService,private router: Router) { super(tableS) }

  summary = [
    { title: 'Đơn mới', count: 345, percentage: '19%', class: 'bg-primary-100 dark:bg-primary-500/10 border-primary-200 dark:border-primary-500/20', icon: 'ri-arrow-right-up-line' },
    { title: 'Đang chờ', count: 120, percentage: '2.98%', class: 'bg-yellow-100 dark:bg-yellow-500/10 border-yellow-200 dark:border-yellow-500/20', icon: 'ri-arrow-right-down-line' },
    { title: 'Thành công', count: 225, percentage: '8.56%', class: 'bg-green-100 dark:bg-green-500/10 border-green-200 dark:border-green-500/20', icon: 'ri-arrow-right-up-line' },
    { title: 'Tổng đơn', count: 9451, percentage: '24.08%', class: 'bg-purple-100 dark:bg-purple-500/10 border-purple-200 dark:border-purple-500/20', icon: 'ri-arrow-right-up-line' }
  ]
  ngOnInit(): void {
    const today = new Date();
    this.queryBookingRequest.bookingDate = today.toISOString().split('T')[0];
  }
  getSelectedData() {

    if (this.selectCategory === 'All' ) {
      this.bookingApiService.getAllBooking(this.queryBookingRequest).subscribe((data : any) => {
        this.gridData = data.data.items;
        this.displayedData = [...this.gridData];
        this.totalItems = data.data.total;
        this.pageSize = this.queryBookingRequest.page.pageSize
        this.currentPage = this.queryBookingRequest.page.pageNum
        this.updateDisplayedData();
      })
    } else {
      this.bookingApiService.getPendingBooking(this.queryBookingRequest).subscribe((data : any) => {
        this.gridData = data.data.items;
        this.displayedData = [...this.gridData];
        this.totalItems = data.data.total;
        this.pageSize = this.queryBookingRequest.page.pageSize
        this.currentPage = this.queryBookingRequest.page.pageNum
        this.updateDisplayedData();
      })
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

  updateBooking(bookingId : number, status : string){
    this.answerBooking.bookingId = bookingId;
    this.answerBooking.bookingStatus = status;
    this.bookingApiService.answerBooking(this.answerBooking).subscribe((data : any) => {
        this.getSelectedData()
    })
  }


  onSelectCategory(category: string) {
    if (this.selectCategory !== category) {
      this.selectCategory = category;
      this.getSelectedData();
    }
  }
}
