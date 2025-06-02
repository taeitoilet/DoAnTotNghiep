import {Component, ElementRef, HostListener, inject, OnInit} from '@angular/core';
import { PageTitleComponent } from '../../../../../../layouts/page-title/page-title.component';
import { DomixGridTestComponent } from '../../../../../../componate/domix-grid-test/domix-grid-test.component';
import { Store } from '@ngrx/store';
import * as ProductActions from '../../store/actions/product.actions';
import * as ProductSelectors from '../../store/selectors/product.selectors';
import { DomixDropdownModule } from '../../../../../../module/domix dropdown/domix-dropdown.module';
import { CommonModule } from '@angular/common';
import { DomixPaginationComponent } from '../../../../../../componate/domix-pagination/domix-pagination.component';
import { DomixTableService } from '../../../../../../componate/domix-grid-test/service/domix-table.service';
import { Router } from '@angular/router';
import { LucideAngularModule } from 'lucide-angular';
import { DomixTooltipModule } from '../../../../../../module/domix tooltip/domix-tooltip.module';
import {Restaurant} from "../../interfaces/restaurant";
import {RestaurantApiService} from "../../../../../../Core/service/restaurant-api-service/restaurant-api.service";
import {RestaurantNearByRequest} from "../../../../../../Core/service/model/RestaurantNearByRequest";
import {VIETNAM_PROVINCES} from "../../../../../../Core/service/model/VIETNAM_PROVINCES";
import {FormsModule} from "@angular/forms";
import {RestaurantQueryRequest} from "../../../../../../Core/service/model/RestaurantQueryRequest";
@Component({
    selector: 'app-apps-ecommerce-products-grid',
  imports: [
    PageTitleComponent,
    DomixDropdownModule,
    CommonModule,
    DomixPaginationComponent,
    LucideAngularModule,
    DomixTooltipModule,
    FormsModule
  ],
    templateUrl: './apps-ecommerce-restaurant-grid.component.html',
    styleUrl: './apps-ecommerce-restaurant-grid.component.scss'
})
export class AppsEcommerceRestaurantGridComponent extends DomixGridTestComponent {
  store = inject(Store);
  restaurant : Restaurant[] = [];
  addCartProductIds: string[] = [];
  wishlistProductIds: string[] = [];
  wishListItemCount: number = 0;
  provinces = VIETNAM_PROVINCES;
  selectedProvince: string | null = null;
  isOpen = false;
  searchRes : string = '';
  restaurantNearByRequest: RestaurantNearByRequest = new RestaurantNearByRequest();
  restaurantQueryRequest : RestaurantQueryRequest = new RestaurantQueryRequest();

  constructor(private eRef: ElementRef,public restApiService: RestaurantApiService,private router: Router, public domiex: DomixTableService) {
    super(domiex);

    this.store.select(ProductSelectors.selectCartItems).subscribe((product) => {
      this.addCartProductIds = product.map((x) => x.pId);
    });
    this.store
      .select(ProductSelectors.selectWishlistTotalItems)
      .subscribe((wishListCount) => {
        this.wishListItemCount = wishListCount;
      });
    this.store
      .select(ProductSelectors.selectWishlistItems)
      .subscribe((wishListItem) => {
        this.wishlistProductIds = wishListItem.map((x) => x.pId);
      });

  }
  goToCart() {
    this.router.navigate(['/dashboard/apps-ecommerce-shop-cart']);
  }

  goToWishlist() {
    this.router.navigate(['/dashboard/apps-ecommerce-wishlist']);
  }
  getActiveRes(){
    this.restaurantQueryRequest.restaurantAddress = this.selectedProvince;
    this.restaurantQueryRequest.restaurantName = this.searchRes
    this.restaurantQueryRequest.page.pageSize = 12
    this.restApiService.getActiveRestaurant(this.restaurantQueryRequest).subscribe((data : any) => {
      this.gridData = data.data.items;
      this.displayedData = [...this.gridData];
      this.totalItems = data.data.total;
      this.pageSize = this.restaurantQueryRequest.page.pageSize
      this.currentPage = this.restaurantQueryRequest.page.pageNum
      this.updateDisplayedData();
    })
  }
  getNearbyRes(){
    this.restaurantNearByRequest.page.pageSize = 12
    this.restaurantNearByRequest.latitude = localStorage.getItem('latitude')
    this.restaurantNearByRequest.longitude = localStorage.getItem('longitude')
    this.restApiService.getNearbyRestaurant(this.restaurantNearByRequest).subscribe((data : any) => {
      this.gridData = data.data.items;
      this.displayedData = [...this.gridData];
      this.totalItems = data.data.total;
      this.pageSize = this.restaurantNearByRequest.page.pageSize
      this.currentPage = this.restaurantNearByRequest.page.pageNum
      this.updateDisplayedData();
    })
  }

  override onPageChange(newPage: number) {
    if(this.selectedProvince || this.searchRes){
      this.restaurantQueryRequest.page.pageNum = newPage;
      window.scrollTo({ top: 0, behavior: 'smooth' });
      this.getActiveRes();
    }else{
      this.restaurantNearByRequest.page.pageNum = newPage;
      window.scrollTo({ top: 0, behavior: 'smooth' });
      this.getNearbyRes();
    }
  }

  override nextPage(): void {
    if(this.selectedProvince || this.searchRes){
      if (this.restaurantQueryRequest.page.pageNum < this.totalPages) {
        this.restaurantQueryRequest.page.pageNum++;
        window.scrollTo({ top: 0, behavior: 'smooth' });
        this.getActiveRes();
      }
    }else{
      if (this.restaurantNearByRequest.page.pageNum < this.totalPages) {
        this.restaurantNearByRequest.page.pageNum++;
        window.scrollTo({ top: 0, behavior: 'smooth' });
        this.getNearbyRes();
      }
    }
  }

  override prevPage(): void {
    if(this.selectedProvince || this.searchRes){
      if (this.restaurantQueryRequest.page.pageNum > 1) {
        this.restaurantQueryRequest.page.pageNum--;
        window.scrollTo({ top: 0, behavior: 'smooth' });
        this.getActiveRes();
      }
    }else{
      if (this.restaurantNearByRequest.page.pageNum > 1) {
        this.restaurantNearByRequest.page.pageNum--;
        window.scrollTo({ top: 0, behavior: 'smooth' });
        this.getNearbyRes();
      }
    }

  }

  override goToPage(page: number): void {
    if(this.selectedProvince || this.searchRes){
      if (page >= 1 && page <= this.totalPages) {
        this.restaurantQueryRequest.page.pageNum = page;
        window.scrollTo({ top: 0, behavior: 'smooth' });
      }
    }else {
      if (page >= 1 && page <= this.totalPages) {
        this.restaurantNearByRequest.page.pageNum = page;
        window.scrollTo({ top: 0, behavior: 'smooth' });
      }
    }
  }

  ngOnInit(): void {
    this.getNearbyRes()
  }

  goToDetail(id: number): void {
    this.router.navigate(['dashboard/apps-ecommerce-restaurant-overview', id]);
  }


  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectProvince(province: string) {
    this.selectedProvince = province;
    this.isOpen = true;
  }
  @HostListener('document:click', ['$event'])
  handleClickOutside(event: MouseEvent) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.isOpen = false;
    }
  }
}
