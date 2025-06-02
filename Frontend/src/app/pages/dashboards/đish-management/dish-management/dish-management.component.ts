import {Component, inject, OnInit} from '@angular/core';
import {CommonModule, CurrencyPipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {DomixDropdownModule} from "../../../../module/domix dropdown/domix-dropdown.module";
import {DomixPaginationComponent} from "../../../../componate/domix-pagination/domix-pagination.component";
import {FooterComponent} from "../../../../layouts/footer/footer.component";
import {FormsModule} from "@angular/forms";
import {LucideAngularModule} from "lucide-angular";
import {NavbarComponent} from "../../../../layouts/navbar/navbar.component";
import {NgSelectComponent, NgSelectModule} from "@ng-select/ng-select";
import {NgxSliderModule} from "@angular-slider/ngx-slider";
import {PageTitleComponent} from "../../../../layouts/page-title/page-title.component";
import {SidebarComponent} from "../../../../layouts/sidebar/sidebar.component";
import {ColDefs, DomixGridTestComponent} from "../../../../componate/domix-grid-test/domix-grid-test.component";
import {Store} from "@ngrx/store";
import {Products} from "../../../Apps/Ecommerce/Products/interfaces/product.model";
import {Observable} from "rxjs";
import {RestApiService} from "../../../../Core/service/rest-api.service";
import {GridSearchService} from "../../../../componate/domix-grid/service/grid-search.service";
import {Router, RouterLink, RouterModule} from "@angular/router";
import {DomixTableService} from "../../../../componate/domix-grid-test/service/domix-table.service";
import * as ProductActions from "../../../Apps/Ecommerce/Products/store/actions/product.actions";
import * as ProductSelectors from "../../../Apps/Ecommerce/Products/store/selectors/product.selectors";
import {Page} from "../../../../Core/service/model/page";
import {DishApiServiceService} from "../../../../Core/service/dish-api-service/dish-api-service.service";
import {RestaurantDishRequest} from "../../../../Core/service/model/RestaurantDishRequest";
import {PopupNotiService} from "../../../../Core/service/popup-noti-service/popup-noti.service";

@Component({
  selector: 'app-dish-management',
  imports: [
    PageTitleComponent,
    RouterModule,
    DomixPaginationComponent,
    FormsModule,
    NgSelectModule,
    CommonModule,
    LucideAngularModule,
    DomixDropdownModule,
    NgxSliderModule,
  ],
  templateUrl: './dish-management.component.html',
  styleUrl: './dish-management.component.scss'
})
export class DishManagementComponent extends DomixGridTestComponent implements OnInit{
  store = inject(Store);
  products: Products[] = [];
  selectedRows: any[] = [];
  public filterConfig: any = {};
  categories!: any[];
  selectedCategory: any | null = null;
  hasHeaderCheckbox = false;
  request: RestaurantDishRequest = new RestaurantDishRequest();
  restaurantId: number = 751;
  showConfirms = false
  showConfirm = false


  public selectedFilters = {
    status: {
      ACTIVE: true,
      INACTIVE: true,
    },
    minPrice: 0,
    maxPrice: 1000,
  };


  columnDefs: ColDefs[] = [
    {
      field: 'dishId',
      headerName: 'Id',
      sortable: true,
      sortDiraction: 'asc',
      headerCheckbox: true,
    },
    {
      field: 'dishName',
      headerName: 'Tên món',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'dishTypeName',
      headerName: 'Phân loại',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'dishPriceValue',
      headerName: 'Giá bán',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'dishStatus',
      headerName: 'Trạng thái',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      headerName: 'Công cụ',
      sortable: false,
    },
  ];

  constructor(
    private apiService: RestApiService,
    private searchService: GridSearchService,
    private router: Router,
    private test: DomixTableService,
    private dishService: DishApiServiceService,
    private popupNoti: PopupNotiService
  ) {
    super(test);

    this.hasHeaderCheckbox = this.columnDefs.some((col) => col.headerCheckbox);
  }

  ngOnInit(): void {
    this.request.restaurantId = this.restaurantId;
  }

  projectData() {
    this.dishService.getRestaurantDishesV2(this.request).subscribe((data : any) => {
      this.products = data.data.items;
      this.gridData = data.data.items;
      this.displayedData = [...this.gridData];
      this.totalItems = data.data.total;
      this.updateDisplayedData();
    })

    this.dishService.getCategoryDish().subscribe((data : any) => {
      this.categories = data.data.items;
    })

    this.updateCheckedRows();
  }

  override onPageChange(newPage: number) {
    this.request.page.pageNum = newPage;
    this.currentPage = newPage;
    this.projectData();
    this.updateCheckedRows();
    //this.updateDisplayedData()
  }

  override nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updateDisplayedData();
      this.updateCheckedRows();
    }
  }

  override prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updateDisplayedData();
      this.updateCheckedRows();
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

  editProduct(pId: string) {
    this.router.navigate([`/dashboard/apps-dish-edit/${pId}`]);
  }

  overviewProduct(pId: string) {
    this.router.navigate([`/dashboard/apps-dish-edit/${pId}`]);
  }

  delProds(dish: any[]) {
    this.showConfirms = false;
    const dishId = dish.map(d => d.dishId);
    this.dishService.deleteDish(dishId).subscribe({
      next: (res) => {
        this.popupNoti.success("Xóa " + dishId.length + " bản ghi thành công!", "Thông báo");
      },
      error: (err) => {
        this.popupNoti.error("Xóa " + dishId.length + " thất bại!", "Thông báo");
      }
    });
    this.projectData();
  }

  delProd(dish: any) {
    const dishId: number[] = [dish.dishId];
    this.showConfirm = false;
    this.dishService.deleteDish(dishId).subscribe({
      next: (res) => {
        this.popupNoti.success("Xóa " + dish.dishName + " thành công!", "Thông báo");
      },
      error: (err) => {
        this.popupNoti.error("Xóa " + dish.dishName + " thất bại!", "Thông báo");
      }
    });

    this.projectData();
  }

  stopPropagation(event: Event) {
    event.stopPropagation();
  }

  onSearch(event: Event) {
    const searchText = (event.target as HTMLInputElement).value;
    this.onSearchChange(searchText);
  }

  catgoryChange() {
    if (this.selectedCategory) {
      this.request = new RestaurantDishRequest();
      this.request.dishTypeId = this.selectedCategory.dishTypeId;
      this.request.restaurantId = this.restaurantId;
      this.projectData()
      this.updateDisplayedData();
    }
  }

  onStatusChange() {
    const filterModel: any = {};

    filterModel.status = {
      type: 'contains',
      filterType: 'text',
      filter: null,
    };

    if (
      this.selectedFilters.status.ACTIVE &&
      !this.selectedFilters.status.INACTIVE
    ) {
      filterModel.status.filter = true;
    } else if (
      !this.selectedFilters.status.ACTIVE &&
      this.selectedFilters.status.INACTIVE
    ) {
      filterModel.status.filter = false;
    } else if (
      !this.selectedFilters.status.ACTIVE &&
      !this.selectedFilters.status.INACTIVE
    ) {
      filterModel.status = {
        type: 'contains',
        filterType: 'text',
        filter: null,
      };
    }

    this.appliedFilters = filterModel;
    this.updateDisplayedData();
  }

  exportData: any[] = [];
  exportRequest: RestaurantDishRequest = new RestaurantDishRequest();
  dishToDel: any;
  exportTable() {
    this.exportRequest.page.pageSize = 0;
    this.dishService.getRestaurantDishesV2(this.exportRequest).subscribe((data : any) => {
      this.exportData = data.data.items;
    })
    if (!this.exportData || this.exportData.length === 0) {
      return;
    }

    let csvContent = '\uFEFF'; // Thêm BOM cho Excel nhận diện UTF-8

    const headers = this.columnDefs
      .map((col) => `"${col.headerName !== 'Công cụ' ? col.headerName : ''}"`)
      .join(',');
    csvContent += headers + '\r\n';

    this.exportData.forEach((product) => {
      const row = this.columnDefs
        .map((col) => {
          const value = this.formatField(product[col.field as keyof typeof product]);
          return `"${value.replace(/"/g, '""')}"`; // Xử lý dấu "
        })
        .join(',');
      csvContent += row + '\r\n';
    });

// Tạo Blob với encoding đúng
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'data.csv';
    link.click();

    document.body.removeChild(link);
  }

  formatField(fieldValue: any): string {
    if (typeof fieldValue === 'boolean') {
      return fieldValue ? 'Published' : 'InActive';
    }
    if (fieldValue instanceof Date) {
      return fieldValue.toLocaleDateString();
    }
    if (typeof fieldValue === 'number') {
      return fieldValue.toFixed(2);
    }
    return fieldValue ? fieldValue.toString() : '';
  }

  override resetFilters(): void {
    this.searchText = '';
    this.appliedFilters = {};
    this.request = new RestaurantDishRequest();
    this.request.restaurantId = this.restaurantId;
    this.projectData();
    // Clear all filters
    //this.updateDisplayedData();
  }
}
