import { Component } from '@angular/core';
import {DomixDropdownModule} from "../../../module/domix dropdown/domix-dropdown.module";
import {DomixPaginationComponent} from "../../../componate/domix-pagination/domix-pagination.component";
import {LucideAngularModule} from "lucide-angular";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {PageTitleComponent} from "../../../layouts/page-title/page-title.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ColDefs, DomixGridTestComponent} from "../../../componate/domix-grid-test/domix-grid-test.component";
import {
  Customer
} from "../../Apps/Ecommerce/Customers/apps-ecommerce-customer-list/apps-ecommerce-customer-list.component";
import {RestApiService} from "../../../Core/service/rest-api.service";
import {DomixTableService} from "../../../componate/domix-grid-test/service/domix-table.service";
import {ModalService} from "../../../Core/service/modal/modal.service";
import {DeleteModalComponent} from "../../../componate/domix-delet-model/delete-modal/delete-modal.component";
import {
  AddEditReviewCustomerComponent
} from "../../Apps/Ecommerce/Customers/apps-ecommerce-customer-list/model/add-edit-review/add-edit-review.component";
import {number} from "echarts";
import {Page} from "../../../Core/service/model/page";
import {RestaurantApiService} from "../../../Core/service/restaurant-api-service/restaurant-api.service";
import {MatDialog} from "@angular/material/dialog";
import {MapModalComponent} from "../res-management/map-modal/map-modal.component";

@Component({
  selector: 'app-pending-res',
  imports: [
    DomixDropdownModule,
    DomixPaginationComponent,
    LucideAngularModule,
    NgForOf,
    NgIf,
    PageTitleComponent,
    ReactiveFormsModule,
    FormsModule,
    NgClass
  ],
  templateUrl: './pending-res.component.html',
  styleUrl: './pending-res.component.scss'
})
export class PendingResComponent extends DomixGridTestComponent{
  allProjectList: Customer[] = [];
  hasHeaderCheckbox = false;
  page: Page = new Page();

  constructor(
    public restApiService: RestaurantApiService,
    public domiex: DomixTableService,
    private modalService: ModalService,
    private dialog: MatDialog
  ) {
    super(domiex);
    this.hasHeaderCheckbox = this.columnDefs.some((col) => col.headerCheckbox);
  }

  ngOnInit(): void {
    this.projectData();
  }

  openMapModal() {
    const dialogRef = this.dialog.open(MapModalComponent, {
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Tọa độ được chọn:', result);
        // Xử lý tọa độ ở đây
      }
    });
  }

  projectData(page: Page = this.page) {
    this.restApiService.getPendingRestaurantList(page).subscribe((data: any) => {
      this.allProjectList = data.data.items;
      this.gridData = data.data.items;
      this.displayedData = [...this.gridData];
      this.totalItems = data.data.total;
      this.updateDisplayedData();
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
    // {
    //   field: 'restaurantAvgRatingText',
    //   headerName: 'Đánh giá trung bình',
    //   sortable: true,
    //   sortDiraction: 'asc',
    // },
    // {
    //   field: 'restaurantIsOpening',
    //   headerName: 'Trạng thái hoạt động',
    //   sortable: true,
    //   sortDiraction: 'asc',
    // },
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
}

