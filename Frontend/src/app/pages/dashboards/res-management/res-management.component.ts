import {Component, ElementRef, ViewChild} from '@angular/core';
import {DomixDropdownModule} from "../../../module/domix dropdown/domix-dropdown.module";
import {DomixPaginationComponent} from "../../../componate/domix-pagination/domix-pagination.component";
import {LucideAngularModule} from "lucide-angular";
import {CommonModule, NgClass, NgForOf, NgIf} from "@angular/common";
import {PageTitleComponent} from "../../../layouts/page-title/page-title.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ColDefs, DomixGridTestComponent} from "../../../componate/domix-grid-test/domix-grid-test.component";
import {DomixTableService} from "../../../componate/domix-grid-test/service/domix-table.service";
import {ModalService} from "../../../Core/service/modal/modal.service";
import {DeleteModalComponent} from "../../../componate/domix-delet-model/delete-modal/delete-modal.component";
import {
  AddEditReviewCustomerComponent
} from "../../Apps/Ecommerce/Customers/apps-ecommerce-customer-list/model/add-edit-review/add-edit-review.component";
import {
  Customer
} from "../../Apps/Ecommerce/Customers/apps-ecommerce-customer-list/apps-ecommerce-customer-list.component";
import {Page} from "../../../Core/service/model/page";
import {RestaurantApiService} from "../../../Core/service/restaurant-api-service/restaurant-api.service";
import {GoogleMapsService} from "../../../Core/service/google-map-service/google-maps.service";

@Component({
  selector: 'app-res-management',
  imports: [
    DomixDropdownModule,
    DomixPaginationComponent,
    LucideAngularModule,
    NgForOf,
    NgIf,
    PageTitleComponent,
    ReactiveFormsModule,
    NgClass,
    FormsModule
  ],
  templateUrl: './res-management.component.html',
  styleUrl: './res-management.component.scss'
})
export class ResManagementComponent extends DomixGridTestComponent{
  @ViewChild('table', { static: false }) tableRef!: ElementRef;
  allProjectList: Customer[] = [];
  hasHeaderCheckbox = false;
  columnWidths: number[] = [];
  page: Page = new Page();
  distances: any[] = [];

  constructor(
    public restApiService: RestaurantApiService,
    public domiex: DomixTableService,
    private modalService: ModalService,
    private googleMapsService: GoogleMapsService
  ) {
    super(domiex);
    this.hasHeaderCheckbox = this.columnDefs.some((col) => col.headerCheckbox);
  }

  ngOnInit(): void {
    // this.projectData(this.page);
    this.columnWidths = this.columnDefs.map(() => 150);
  }
  projectData(page: Page = this.page) {
    this.restApiService.getRestaurantList(page).subscribe((data: any) => {
      this.allProjectList = data.data.items;
      this.gridData = data.data.items;
      this.displayedData = [...this.gridData];
      this.totalItems = data.data.total;
      this.updateDisplayedData();
      for (let data of this.displayedData) {
        this.distances[data.restaurantId] = this.googleMapsService.calculateDistance(Number(localStorage.getItem('latitude')), Number(localStorage.getItem('longitude')), data.restaurantLatitude, data.restaurantLongitude);
        console.log(this.distances[data.restaurantId]);
      }
    });
  }


  override onPageChange(newPage: number) {
    this.page.pageNum = newPage;
    this.currentPage = newPage;
    this.projectData(this.page);
    //this.updateDisplayedData()
  }

  override nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updateDisplayedData();
    }
  }

  override prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updateDisplayedData();
    }
  }

  override goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      //this.page.pageNum = page;
      this.currentPage = page;
      //this.projectData();
      // this.updateDisplayedData();
    }
  }

  columnDefs: ColDefs[] = [
    {
      field: 'restaurantId',
      headerName: 'ID',
      sortable: true,
      sortDiraction: 'asc',
      headerCheckbox: true,
    },
    {
      field: 'restaurantName',
      headerName: 'Tên Nhà Hàng',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'restaurantDescription',
      headerName: 'Mô tả',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'restaurantPhone',
      headerName: 'Số điện thoại',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'restaurantAvgRatingText',
      headerName: 'Đánh giá trung bình',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'restaurantIsOpening',
      headerName: 'Trạng thái hoạt động',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'restaurantAddress',
      headerName: 'Địa chỉ',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'restaurantIsAds',
      headerName: 'Trạng thái quảng cáo',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      headerName: 'Công cụ',
      sortable: false,
    },
  ];
  deleteModal(index: number) {
    this.modalService.open(DeleteModalComponent, {}, (result) => {
      if (result) {
        this.displayedData.splice(index, 1);
      }
    });
  }
  openCreateModal(projectdata: any | null, index: number | null) {
    const data = projectdata;
    console.log("This is staff component");

    this.modalService.open(AddEditReviewCustomerComponent, data, (result) => {
      if (result) {
        if (result) {
          if (index !== null && index > -1) {
            this.gridData[index] = {
              ...this.gridData[index],
              ...result,
            };
            this.displayedData = [...this.gridData];
            this.updateDisplayedData();
          } else {
            this.gridData.unshift(result);
            this.displayedData = [...this.gridData];
            this.updateDisplayedData();
          }
        }
      }
    });
  }

  initResize(event: MouseEvent, index: number) {
    const startX = event.pageX;
    const startWidth = this.columnWidths[index];

    const onMouseMove = (e: MouseEvent) => {
      const delta = e.pageX - startX;
      const newWidth = Math.max(80, startWidth + delta); // giới hạn min width
      this.columnWidths[index] = newWidth;
    };

    const onMouseUp = () => {
      document.removeEventListener('mousemove', onMouseMove);
      document.removeEventListener('mouseup', onMouseUp);
    };

    document.addEventListener('mousemove', onMouseMove);
    document.addEventListener('mouseup', onMouseUp);
  }

  calculateDistance(res_lat: number, res_lng: number) {
    const lat = localStorage.getItem('latitude');
    const lng = localStorage.getItem('longitude');
    let distance = 0;
    const currentLocation = {
      lat: lat ? Number(lat) : 0,
      lng: lng ? Number(lng) : 0
    }
    const targetLocation = {
      lat: res_lat,
      lng: res_lng
    }
    this.googleMapsService.getDistance(currentLocation, targetLocation)
      .then((result: any) => {
        distance = result.distance;
        console.log(distance);
      })
      .catch(error => {
        console.error(error);
      });
    return distance;
  }
}
