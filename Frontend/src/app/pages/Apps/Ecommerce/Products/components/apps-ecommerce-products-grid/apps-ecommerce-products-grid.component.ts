import {Component, ElementRef, HostListener, inject} from '@angular/core';
import { PageTitleComponent } from '../../../../../../layouts/page-title/page-title.component';
import { DomixGridTestComponent } from '../../../../../../componate/domix-grid-test/domix-grid-test.component';
import { Store } from '@ngrx/store';
import * as ProductActions from '../../store/actions/product.actions';
import * as ProductSelectors from '../../store/selectors/product.selectors';
import { Products } from '../../interfaces/product.model';
import {Observable, take} from 'rxjs';
import { DomixDropdownModule } from '../../../../../../module/domix dropdown/domix-dropdown.module';
import { CommonModule } from '@angular/common';
import { DomixPaginationComponent } from '../../../../../../componate/domix-pagination/domix-pagination.component';
import { DomixTableService } from '../../../../../../componate/domix-grid-test/service/domix-table.service';
import { Router } from '@angular/router';
import { LucideAngularModule } from 'lucide-angular';
import { CartData } from '../apps-ecommerce-shop-cart/apps-ecommerce-shop-cart.component';
import { DomixTooltipModule } from '../../../../../../module/domix tooltip/domix-tooltip.module';
import { WishListData } from '../apps-ecommerce-wishlist/apps-ecommerce-wishlist.component';
import {FormsModule} from "@angular/forms";
import {VIETNAM_PROVINCES} from "../../../../../../Core/service/model/VIETNAM_PROVINCES";
import {QueryDishRequest} from "../../../../../../Core/service/model/dishRequests";
import {Page} from "../../../../../../Core/service/model/page";
import {DishApiServiceService} from "../../../../../../Core/service/dish-api-service/dish-api-service.service";
import {data} from "autoprefixer";
import {dish, dishType} from "../../../../../../Core/service/model/dish";
import {ModalService} from "../../../../../../Core/service/modal/modal.service";

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
    templateUrl: './apps-ecommerce-products-grid.component.html',
    styleUrl: './apps-ecommerce-products-grid.component.scss'
})
export class AppsEcommerceProductsGridComponent extends DomixGridTestComponent {
  store = inject(Store);
  addCartProductIds: string[] = [];
  wishlistProductIds: string[] = [];
  wishListItemCount: number = 0;
  isOpen = false;
  selectedDishType : string | null = null;
  dishType : dishType[] = []
  dishTypeNames : (string | null)[] = []
  queryDishRequest : QueryDishRequest = new QueryDishRequest();
  page : Page = new Page();
  searchDish = ''
  dish : dish = new dish();
  dishId : number = 0;
  showDeleteModal: boolean = false;
  pendingProduct: dish | null = null;

  constructor(private modalService: ModalService,public dishApiServiceService : DishApiServiceService,private eRef: ElementRef,private router: Router, public domiex: DomixTableService) {
    super(domiex);

    this.store.select(ProductSelectors.selectCartItems).subscribe((product) => {
      this.addCartProductIds = product.map((x) => x.pId);
      console.log(this.addCartProductIds);
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

  removeFromCart(pid: string) {
    this.store.dispatch(ProductActions.removeFromCart({ productId: pid }));
  }

  addToCart(product: dish) {
    const newDishRestaurant = product.restaurant;

    this.store.select(ProductSelectors.selectCartItems).pipe(take(1)).subscribe((cartItems) => {
      if (cartItems.length === 0) {
        this.addProductToCart(product);
        return;
      }else{
        const isSameRestaurant = cartItems.every(item => item.restaurant === newDishRestaurant);

        if (isSameRestaurant) {
          this.addProductToCart(product);
        } else {
          this.pendingProduct = product;
          this.showDeleteModal = true;
        }
      }
    });
  }

  addProductToCart(product: dish) {
    const cartData: CartData = {
      productName: product.dishName,
      category: product.dishTypeName,
      qty: 1,
      discount: 0,
      price: product.dishSalePrice,
      image: product.dishImageUrl,
      selectedSize: '',
      selectedColor: '',
      color: [],
      size: [],
      pId: product.dishId,
      restaurant: product.restaurant,
      restaurantId: product.restaurantId,
    };
    this.store.dispatch(ProductActions.addToCart({ product: cartData }));
  }

  removeFromWishlist(pId: string) {
    this.store.dispatch(ProductActions.removeFromWishlist({ productId: pId }));
  }

  addToWishList(product: dish) {
    const wishListData: WishListData = {
      productName: product.dishName,
      category: product.dishTypeName,
      qty: 1,
      discount: 0,
      price: product.dishSalePrice,
      image: product.dishImageUrl,
      selectedSize: '',
      selectedColor: '',
      color:[],
      size: [],
      pId: product.dishId,
      restaurant: product.restaurant,
      restaurantId: product.restaurantId,
    };

    this.store.dispatch(
      ProductActions.addToWishlist({ product: wishListData })
    );
  }

  goToCart() {
    this.router.navigate(['/dashboard/apps-ecommerce-shop-cart']);
  }

  goToWishlist() {
    this.router.navigate(['/dashboard/apps-ecommerce-wishlist']);
  }

  ngOnInit(): void {
    this.getListDish()
    this.getDishType()
  }
  getDetailDish(id : string){
    this.dishApiServiceService.getDetailDishes(id).subscribe((data : any) => {
      this.dish = data.data;
    })
  }
  getListDish(){
    this.queryDishRequest.page.pageSize = 52
    this.queryDishRequest.dishName = this.searchDish
    if(this.selectedDishType === 'Tất cả'){
      this.queryDishRequest.dishType = null
    }else{
      this.queryDishRequest.dishType = this.selectedDishType
    }

    this.dishApiServiceService.getListDishes(this.queryDishRequest).subscribe((data : any) => {
      this.gridData = data.data.items;
      this.displayedData = [...this.gridData];
      this.totalItems = data.data.total;
      this.pageSize = this.queryDishRequest.page.pageSize
      this.currentPage = this.queryDishRequest.page.pageNum
      this.updateDisplayedData();
    })
  }
  getDishType(){
    this.page.pageSize = 100
    this.dishApiServiceService.getCategoryDish(this.page).subscribe((data : any) => {
      this.dishType = data.data.items;
      this.dishTypeNames = this.dishType.map(t => t.dishTypeName)
    })
  }
  override onPageChange(newPage: number) {
    this.queryDishRequest.page.pageNum = newPage;
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.getListDish();

  }

  override nextPage(): void {
    this.queryDishRequest.page.pageNum++;
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.getListDish();
  }

  override prevPage(): void {
    if (this.queryDishRequest.page.pageNum > 1) {
      this.queryDishRequest.page.pageNum--;
      window.scrollTo({ top: 0, behavior: 'smooth' });
      this.getListDish();
    }
  }

  override goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      window.scrollTo({ top: 0, behavior: 'smooth' });
      this.queryDishRequest.page.pageNum = page;
    }
  }
  @HostListener('document:click', ['$event'])
  handleClickOutside(event: MouseEvent) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.isOpen = false;
    }
  }
  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }
  onImageError(event: any) {
    event.target.src = 'assets/images/food.png';
  }

  selectDishType(type: string | null){
    this.selectedDishType = type;
    this.isOpen = true;
  }
  goToDishDetail(id:number): void {
    this.router.navigate(['dashboard/apps-ecommerce-dish-overview',id]);
  }

  closeaDeletModal(confirmDelete: boolean = false) {
    this.showDeleteModal = false;

    if (confirmDelete && this.pendingProduct) {
      // Xóa toàn bộ giỏ hàng
      for (let id of this.addCartProductIds) {
        this.removeFromCart(id);
      }
      // Thêm món mới
      this.addProductToCart(this.pendingProduct);
    }

    this.pendingProduct = null;
  }
}
