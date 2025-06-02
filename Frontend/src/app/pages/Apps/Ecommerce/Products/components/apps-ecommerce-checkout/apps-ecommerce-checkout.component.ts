import {Component, inject, ViewChild} from '@angular/core';
import { FinalOrderSummaryComponent } from '../final-order-summary/final-order-summary.component';
import { PageTitleComponent } from '../../../../../../layouts/page-title/page-title.component';
import { CommonModule } from '@angular/common';
import { LucideAngularModule } from 'lucide-angular';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import {OrderApiServiceService} from "../../../../../../Core/service/orders-api-service/order-api-service.service";
import {Store} from "@ngrx/store";
import * as ProductSelectors from "../../store/selectors/product.selectors";
import {createOrder, createOrderItemRequest, orders} from "../../../../../../Core/service/model/orders";
import * as ProductActions from "../../store/actions/product.actions";

export interface CartItem {
  subtotal: number;         // Total of all product prices
  vat: number;              // VAT amount (6%)
  discount: number;         // Discount amount (e.g., 10%)
  shippingCharge: number;   // Shipping charge
  total: number;            // Final total
}

export interface CartData {
  productName: string;
  category: string;
  qty: number;
  discount: number;
  price: number;
  displayedPrice?: number;
  image: string;
  selectedSize: string;
  selectedColor: string;
  color: string[];
  size: string[];
  pId: string;
  restaurant : string;
}

@Component({
    selector: 'app-apps-ecommerce-checkout',
    imports: [FinalOrderSummaryComponent, RouterModule, PageTitleComponent, CommonModule, LucideAngularModule, ReactiveFormsModule],
    templateUrl: './apps-ecommerce-checkout.component.html',
    styleUrl: './apps-ecommerce-checkout.component.scss'
})
export class AppsEcommerceCheckoutComponent {
  @ViewChild(FinalOrderSummaryComponent) finalOrderSummary! : FinalOrderSummaryComponent;
  store = inject(Store);
  addToCartProduct: string[] = [];
  addressForm!: FormGroup;
  clickedIndex: null | number = null;
  createOrderRequest : createOrder = new createOrder()
  orders : orders = new orders()
  showSuccessModal : boolean = false

  constructor(public router: Router,private fb: FormBuilder, public orderApiService : OrderApiServiceService) {
    this.store.select(ProductSelectors.selectCartItems).subscribe((product) => {
      if (product.length) {
        this.addToCartProduct = [];
        this.addToCartProduct = product.map((x) => x.pId);
      }
    });
  }

  ngOnInit(): void {
    this.addressForm = this.fb.group({
      lastName: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern(/^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5]|9[0-9])[0-9]{7}$/)]],
      address: ['', Validators.required],
      selected: [true],
    });
  }
  loginUserAddress = [
    {
      "type": "Work",
      "lastName": "Minh Quý",
      "phone": "0378932206",
      "address": "Số 100 ngõ 136, đường Cầu Diễn, Phường Minh Khai, Quận Bắc Từ Liêm, Thành phố Hà Nội",
      "selected": false
    }
  ]

  selectAdd(address: any) {
    this.loginUserAddress.forEach(element => {
      element.selected = false;
    });

    address.selected = !address.selected;
    this.addressForm.patchValue(address);
  }

  onSubmit() {
    if (this.addressForm.valid) {
      if (this.clickedIndex !== null) {
        this.loginUserAddress[this.clickedIndex] = this.addressForm.value;
      } else {
        this.loginUserAddress.forEach(element => {
          element.selected = false;
        });
        this.loginUserAddress.unshift(this.addressForm.value);
      }
      this.addressForm.reset();
      this.clickedIndex = null;
    } else {
      this.addressForm.markAsTouched()
      this.addressForm.markAllAsTouched();
      this.addressForm.updateValueAndValidity();
    }
  }
  onImageError(event: any) {
    event.target.src = 'assets/images/food.png';
  }
  sendOrder(){
    if(this.addressForm.valid){
      this.orderApiService.createOrder(this.createOrderRequest).subscribe((data : any) => {
        this.orders = data.data
      })
      this.openSuccessModal()
    }
    else{
      this.addressForm.markAllAsTouched();
    }
  }
  closeSuccessModal() {
    this.showSuccessModal = false;
  }

  openSuccessModal() {
    this.showSuccessModal = true;
    setTimeout(() => {
      this.showSuccessModal = false;
      this.router.navigate(['/dashboard/apps-ecommerce-products-grid']);
      if( this.addToCartProduct.length > 0 ){
        for (let id of this.addToCartProduct) {
          this.removeFromCart(id);
        }
      }
    }, 4000);

  }
  showConfirmModal = false;

  openConfirmModal() {
    const summary = this.finalOrderSummary.cartSummary;
    const items = this.finalOrderSummary.cartItems;
    if(items.length > 0){
      if(!this.addressForm.valid){
        this.addressForm.markAllAsTouched()
      }else{
        this.showConfirmModal = true;
        this.createOrderRequest.userName = this.addressForm.value.lastName;
        this.createOrderRequest.userPhone = this.addressForm.value.phone;
        this.createOrderRequest.orderDeliveryAddress = this.addressForm.value.address;
        this.createOrderRequest.orderItems = items.map(cartItem => {
          return {
            dishId: +cartItem.pId,
            orderItemPrice: cartItem.price,
            orderItemQuantity: cartItem.qty,
            orderItemNote: ''
          } as createOrderItemRequest;
        })
        this.createOrderRequest.orderTotalAmount =summary.total
        this.createOrderRequest.restaurantId = items[0].restaurantId
      }
    }
    else{
      this.router.navigate(['/dashboard/apps-ecommerce-products-grid']);
    }

  }
  confirmOrders() {
    this.showConfirmModal = false;
    this.sendOrder();
  }


  cancelConfirmModal() {
    this.showConfirmModal = false;
  }
  removeFromCart(pid: string) {
    this.store.dispatch(ProductActions.removeFromCart({ productId: pid }));
  }

}
