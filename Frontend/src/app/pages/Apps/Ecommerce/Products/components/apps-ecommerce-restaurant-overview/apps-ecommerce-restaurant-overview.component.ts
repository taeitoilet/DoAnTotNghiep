import {Component, ElementRef, inject, ViewChild} from '@angular/core';
import { PageTitleComponent } from '../../../../../../layouts/page-title/page-title.component';
import { register } from 'swiper/element';
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
import * as ProductSelectors from "../../store/selectors/product.selectors";
import {take} from "rxjs";
import {CartData} from "../apps-ecommerce-shop-cart/apps-ecommerce-shop-cart.component";
import * as ProductActions from "../../store/actions/product.actions";
import {Store} from "@ngrx/store";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { Ng2FlatpickrModule } from 'ng2-flatpickr';
import {BookingApiServiceService} from "../../../../../../Core/service/booking-api-service/booking-api-service.service";
import {booking, createBookingRequest} from "../../../../../../Core/service/model/booking";
// import {RestaurantDishRequest} from "../../../../../../Core/service/model/RestaurantDishRequest";


@Component({

    selector: 'app-apps-ecommerce-product-overview',
  imports: [PageTitleComponent, CommonModule, LucideAngularModule, DomixPaginationComponent, DomixDropdownModule, ReactiveFormsModule],
    templateUrl: './apps-ecommerce-restaurant-overview.component.html',
    styleUrl: './apps-ecommerce-restaurant-overview.component.scss'
})
export class AppsEcommerceRestaurantOverviewComponent {
  constructor(public bookingApiService: BookingApiServiceService,public restaurantApiService: RestaurantApiService,public dishApiService: DishApiServiceService, private modalService: ModalService, private activatedRoute: ActivatedRoute, private router: Router,private fb: FormBuilder) {
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
  @ViewChild('paginationDynamicSlider') paginationDynamicSliderContainer!: ElementRef;
  @ViewChild('paginationContain') paginationContain!: ElementRef;
  restaurant : Restaurant = new Restaurant();
  store = inject(Store);
  dishes: dish[] = [];
  dish : dish = new dish();
  dishCategory? : string[]
  selectedCategoryIndex: number | null = null;
  addCartProductIds: string[] = [];
  wishlistProductIds: string[] = [];
  wishListItemCount: number = 0;
  currentPage: number = 1;
  itemsPerPage: number = 20;
  totalPages: number = 0;
  showingStart: number = 1;
  showingEnd: number = 0;
  restaurantId : number = 0;
  showDeleteModal: boolean = false;
  pendingProduct: dish | null = null;
  getResDishRequest : GetResDishRequest = new GetResDishRequest();
  bookingForm!: FormGroup;
  bookingButton : boolean = false;
  // request: RestaurantDishRequest = new RestaurantDishRequest();
  displayDish: any
  dishShow?: any
  today: string = '';
  hoursOptions: string[] = [];
  createBookingRequest : createBookingRequest = new createBookingRequest()
  booking : booking = new booking()
  showSuccessModal : boolean = false

  ngOnInit(): void {
    register();
    this.bookingForm = this.fb.group({
      lastName: ['', Validators.required],
      phone: ['', [Validators.required,  Validators.pattern(/^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5]|9[0-9])[0-9]{7}$/)]],
      time: [null, Validators.required],
      guestCount: ['', [Validators.required, Validators.min(1)]],
      note: ['', Validators.required]
    });
    const now = new Date();
    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0');
    const day = now.getDate().toString().padStart(2, '0');
    this.today = `${year}-${month}-${day}`;
    this.restaurantId = Number(this.activatedRoute.snapshot.paramMap.get('id'))
    for (let h = 0; h < 24; h++) {
      this.hoursOptions.push(this.formatTime(h, 0));
      this.hoursOptions.push(this.formatTime(h, 30));
    }
    this.getDetails(this.restaurantId)
    this.getRestaurantDishes();
    this.getDishesByType('ALL');
    console.log(this.today)
  }
  formatTime(hour: number, minute: number): string {
    const pad = (n: number) => n.toString().padStart(2, '0');
    return `${pad(hour)}:${pad(minute)}`;
  }
  getDetails(id:number): void {
    this.restaurantApiService.getRestaurantDetail(id).subscribe((data : any) => {
      this.restaurant = data.data;
    })
  }
  getRestaurantDishes(){

    this.getResDishRequest.page.pageSize = 0
    this.getResDishRequest.restaurantId = this.restaurantId
    this.dishApiService.getRestaurantDishes(this.getResDishRequest).subscribe((data : any) => {

    // this.request.restaurantId = this.restaurantId;
    // this.request.page.pageSize = 0;
    // this.dishApiService.getRestaurantDishes(this.request).subscribe((data : any) => {
      this.dishes = data.data.items
      this.dishCategory = [...new Set(this.dishes?.map(d => d.dishTypeName))]
      if(this.dishes){this.dish = this.dishes[0]}
      this.getDishesByType('ALL')
    })
  }
  getDishesByType(type: string) {
    if(type == 'ALL'){
      this.dishShow = this.dishShow = [...this.dishes || []]
      this.totalPages = Math.ceil(this.dishShow.length / this.itemsPerPage);
      this.paginateDishRes();
    }else{
      this.dishShow = [...this.dishes?.filter(d => d.dishTypeName === type) || []]
      this.totalPages = Math.ceil(this.dishShow.length / this.itemsPerPage);
      this.paginateDishRes();
    }
  }

  paginateDishRes() {
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
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.paginateDishRes();
  }
  onImageError(event: any) {
    event.target.src = 'assets/images/42877472-restaurant-icon.jpg';
  }
  onDishImageError(event: any) {
    event.target.src = 'assets/images/food.png';
  }
  goToDishDetail(id:number): void {
    this.router.navigate(['dashboard/apps-ecommerce-dish-overview',id]);
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
      restaurantId : product.restaurantId
    };
    this.store.dispatch(ProductActions.addToCart({ product: cartData }));
  }
  removeFromCart(pid: string) {
    this.store.dispatch(ProductActions.removeFromCart({ productId: pid }));
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
  onSubmit() {
    if (this.bookingForm.valid) {
      this.bookingApiService.createBooking(this.createBookingRequest).subscribe((data: any) => {
        this.booking = data.data
      })
      this.bookingButton = false
      this.openSuccessModal();
      this.bookingForm.reset
    }else{
      this.bookingForm.markAllAsTouched();
    }
  }
  closeSuccessModal() {
    this.showSuccessModal = false;
  }
  openSuccessModal() {
    this.showSuccessModal = true;

    setTimeout(() => {
      this.showSuccessModal = false;
    }, 3000); // 3000 ms = 3 giây
  }
  showConfirmModal = false;

  openConfirmModal() {
    if (this.bookingForm.valid) {
      this.showConfirmModal = true;
      this.createBookingRequest.restaurantId = this.restaurantId
      this.createBookingRequest.bookingDate = this.today
      this.createBookingRequest.bookingTime = this.bookingForm.value.time
      this.createBookingRequest.bookingNumberOfPeople = this.bookingForm.value.guestCount
      this.createBookingRequest.bookingNote = this.bookingForm.value.note
      this.createBookingRequest.bookingUserName = this.bookingForm.value.lastName
      this.createBookingRequest.bookingUserPhone = this.bookingForm.value.phone
    }else{
      this.bookingForm.markAllAsTouched();
    }

  }


  confirmBooking() {
    this.showConfirmModal = false;
    this.onSubmit();
  }


  cancelConfirmModal() {
    this.showConfirmModal = false;
  }
}
