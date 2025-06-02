import {Component, ElementRef, inject, ViewChild} from '@angular/core';
import { PageTitleComponent } from '../../../../../../layouts/page-title/page-title.component';
import { register } from 'swiper/element';
import Swiper from 'swiper';
import { CommonModule } from '@angular/common';
import { LucideAngularModule } from 'lucide-angular';
import { ModalService } from '../../../../../../Core/service/modal/modal.service';
import { DomixPaginationComponent } from '../../../../../../componate/domix-pagination/domix-pagination.component';
import { DomixDropdownModule } from '../../../../../../module/domix dropdown/domix-dropdown.module';
import { ActivatedRoute, Router } from '@angular/router';
import {RestaurantApiService} from "../../../../../../Core/service/restaurant-api-service/restaurant-api.service";
import {Restaurant} from "../../interfaces/restaurant";
import {dish} from "../../../../../../Core/service/model/dish";
import {DishApiServiceService} from "../../../../../../Core/service/dish-api-service/dish-api-service.service";

import {GetResDishRequest} from "../../../../../../Core/service/model/dishRequests";
import * as ProductActions from "../../store/actions/product.actions";
import * as ProductSelectors from "../../store/selectors/product.selectors";
import {take} from "rxjs";
import {CartData} from "../apps-ecommerce-shop-cart/apps-ecommerce-shop-cart.component";
import {Store} from "@ngrx/store";


@Component({
    selector: 'app-apps-ecommerce-product-overview',
    imports: [PageTitleComponent, CommonModule, LucideAngularModule, DomixPaginationComponent, DomixDropdownModule],
    templateUrl: './apps-ecommerce-dish-overview.component.html',
    styleUrl: './apps-ecommerce-dish-overview.component.scss'
})
export class AppsEcommerceDishOverviewComponent {
  constructor(public restaurantApiService: RestaurantApiService,public dishApiService: DishApiServiceService, private modalService: ModalService, private activatedRoute: ActivatedRoute, private router: Router) {
    this.store.select(ProductSelectors.selectCartItems).subscribe((product) => {
      this.addCartProductIds = product.map((x) => x.pId);
      console.log(this.addCartProductIds);
    });
  }
  @ViewChild('paginationDynamicSlider') paginationDynamicSliderContainer!: ElementRef;
  @ViewChild('paginationContain') paginationContain!: ElementRef;
  store = inject(Store);
  restaurant? : Restaurant ;
  dishes: dish[] = [];
  dish : dish = new dish();
  dishCategory? : string[]
  selectedCategoryIndex: number | null = null;
  productName = 'Collection Ruffled Cotton Top';
  rating = 4.8;
  currentPage: number = 1;
  itemsPerPage: number = 20;
  totalPages: number = 0;
  showingStart: number = 1;
  showingEnd: number = 0;
  restaurantId : number = 0;
  // request: RestaurantDishRequest = new RestaurantDishRequest();
  dishId : string = '';
  count = 1;
  displayDish: any
  dishShow?: any
  showDeleteModal: boolean = false;
  pendingProduct: dish | null = null;
  getResDishRequest : GetResDishRequest = new GetResDishRequest();
  addCartProductIds: string[] = [];

  ngOnInit(): void {
    register();
    this.dishId = Number(this.activatedRoute.snapshot.paramMap.get('id')).toString();
    this.getDetails(this.dishId)

  }
  getDetails(id:string): void {
    this.dishApiService.getDetailDishes(id).subscribe((data : any) => {
      this.dish = data.data;
      this.getRestaurantDishes()
    })
  }
  getRestaurantDishes(){

    this.getResDishRequest.page.pageSize = 0
    this.getResDishRequest.restaurantId = this.dish.restaurantId
    this.dishApiService.getRestaurantDishes(this.getResDishRequest).subscribe((data : any) => {

    // this.request.restaurantId = this.restaurantId;
    // this.request.page.pageSize = 0;
    // this.dishApiService.getRestaurantDishes(this.request).subscribe((data : any) => {

      this.dishes = data.data.items
      this.dishCategory = [...new Set(this.dishes?.map(d => d.dishTypeName))]
      this.getDishesByType('ALL')
    })
  }
  getDishesByType(type: string) {
    if(type == "ALL"){
      this.dishShow = this.dishShow = [...this.dishes || []]
      this.selectedCategoryIndex = null
      this.totalPages = Math.ceil(this.dishShow.length / this.itemsPerPage);
      this.paginateReview();
    }else{
      this.dishShow = [...this.dishes?.filter(d => d.dishTypeName === type) || []]
      this.totalPages = Math.ceil(this.dishShow.length / this.itemsPerPage);
      this.paginateReview();
    }
  }

  paginateReview() {
    this.showingStart = (this.currentPage - 1) * this.itemsPerPage + 1;
    this.showingEnd = Math.min(this.currentPage * this.itemsPerPage, this.dishShow.length);
    this.displayDish = this.dishShow.slice(this.showingStart - 1, this.showingEnd);
  }

  getStarClass(starRating: string | number, index: number): string {
    const roundedRating = parseFloat(starRating.toString()); // Ensure it's a number
    if (index <= roundedRating) {
      return 'ri-star-fill';
    } else if (index - 1 < roundedRating && roundedRating % 1 !== 0) {
      return 'ri-star-half-fill';
    }
    return 'ri-star-line';
  }

  onPageChange(newPage: number) {
    this.currentPage = newPage;
    this.paginateReview();
  }
  onImageError(event: any) {
    event.target.src = 'assets/images/42877472-restaurant-icon.jpg';
  }
  increaseCount() {
    this.count++;
  }

  decreaseCount() {
    if (this.count > 1) this.count--;
  }
  goToRestaurant() {
    this.router.navigate(['dashboard/apps-ecommerce-restaurant-overview', this.dish.restaurantId]);
  }
  removeFromCart(pid: string) {
    this.store.dispatch(ProductActions.removeFromCart({ productId: pid }));
  }
  addToCart(product: dish,qty : number = 1) {
    const newDishRestaurant = product.restaurant;
    this.store.select(ProductSelectors.selectCartItems).pipe(take(1)).subscribe((cartItems) => {
      if (cartItems.length === 0) {
        this.addProductToCart(product,qty);
        return;
      }else{
        const isSameRestaurant = cartItems.every(item => item.restaurant === newDishRestaurant);

        if (isSameRestaurant) {
          this.addProductToCart(product,qty);
        } else {
          this.pendingProduct = product;
          this.showDeleteModal = true;
        }
      }
    });
  }
  goToCheckout(){
    this.router.navigate(['dashboard/apps-ecommerce-checkout']);
  }
  addProductToCart(product: dish,qty : number = 1) {
    const cartData: CartData = {
      productName: product.dishName,
      category: product.dishTypeName,
      qty: qty,
      discount: 0,
      price: product.dishSalePrice,
      image: product.dishImageUrl,
      selectedSize: '',
      selectedColor: '',
      color: [],
      size: [],
      pId: product.dishId,
      restaurant: product.restaurant,
      restaurantId : product.restaurantId
    };
    this.store.dispatch(ProductActions.addToCart({ product: cartData }));
  }

  closeaDeletModal(confirmDelete: boolean = false) {
    this.showDeleteModal = false;
    if (confirmDelete && this.pendingProduct) {
      for (let id of this.addCartProductIds) {
        this.removeFromCart(id);
      }
      this.addProductToCart(this.pendingProduct);
    }
    this.pendingProduct = null;
  }
  onDishImageError(event: any) {
    event.target.src = 'assets/images/food.png';
  }
}
