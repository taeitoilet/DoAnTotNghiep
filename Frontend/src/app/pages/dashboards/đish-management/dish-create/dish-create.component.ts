import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {LucideAngularModule} from "lucide-angular";
import {DecimalPipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {NgSelectComponent} from "@ng-select/ng-select";
import {PageTitleComponent} from "../../../../layouts/page-title/page-title.component";
import {Observable, of, switchMap} from "rxjs";
import {Products} from "../../../Apps/Ecommerce/Products/interfaces/product.model";
import {ActivatedRoute, Router} from "@angular/router";
import {Store} from "@ngrx/store";
import * as ProductActions from "../../../Apps/Ecommerce/Products/store/actions/product.actions";
import * as ProductSelectors from "../../../Apps/Ecommerce/Products/store/selectors/product.selectors";
import Swiper from "swiper";
import {deleteProduct, updateProduct} from "../../../Apps/Ecommerce/Products/store/actions/product.actions";
import {dish} from "../../../../Core/service/model/dish";
import {HttpClient} from "@angular/common/http";
import {DishApiServiceService} from "../../../../Core/service/dish-api-service/dish-api-service.service";
import {tap} from "rxjs/operators";
import {ToastrModule, ToastrService} from "ngx-toastr";
import {PopupNotiService} from "../../../../Core/service/popup-noti-service/popup-noti.service";

@Component({
  selector: 'app-dish-create',
  imports: [
    FormsModule,
    LucideAngularModule,
    NgIf,
    NgSelectComponent,
    PageTitleComponent,
    ReactiveFormsModule,
    DecimalPipe,
    ToastrModule
  ],
  templateUrl: './dish-create.component.html',
  styleUrl: './dish-create.component.scss'
})
export class DishCreateComponent implements OnInit{
  products$!: Observable<Products[]>;
  currantSize!:string;
  currantColor!:string;
  tmpDish: dish = new dish();
  categories: any[] = [];
  toEditDish: dish = new dish();
  showConfirm = false

  constructor(private fb: FormBuilder,
              public activatedRoute: ActivatedRoute,
              private store: Store,
              private router: Router,
              private http: HttpClient,
              private dishService: DishApiServiceService,
              private popupNoti: PopupNotiService
  ) {

    this.formGroup = this.fb.group({
      productName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(80)]),
      category: new FormControl(null, Validators.required),
      price: new FormControl('', Validators.required),
      // revenue: new FormControl(''),
      //qty: new FormControl(1, Validators.required),
      image: new FormControl('',Validators.required),
      //image1: new FormControl('',Validators.required),
      //image2: new FormControl('',Validators.required),
      status: new FormControl(false),
      // cashOnDelivery: new FormControl(false),
      // visaAndMaster: new FormControl(false),
      // bankTransfer: new FormControl(false),

      //size: new FormControl([], Validators.required),
      //colores: new FormControl([], Validators.required),
      //brand: new FormControl(null, Validators.required),
      //gender: new FormControl('', Validators.required),
      description: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]),
      //stock: new FormControl(1, Validators.required),
      discount: new FormControl('', [Validators.required, Validators.min(5), Validators.max(100)]),
      sellingPrice: new FormControl({ value: 0, disabled: true }, Validators.required),
      productID: new FormControl(),
    })
  }

  ngOnInit() {
    this.dishService.getCategoryDish().pipe(
      tap((data: any) => {
        this.categories = data.data.items;
      }),
      switchMap(() => {
        this.productId = this.activatedRoute.snapshot.params['productId'];
        if (this.productId) {
          return this.dishService.getDishDetail(this.productId);
        } else {
          return of(null); // trả về Observable rỗng nếu không có productId
        }
      })
    ).subscribe((data: any) => {
      if (data?.data) {
        const dish = data.data;
        const foundCategory = this.categories.find(c => c.dishTypeName === dish.dishTypeName);
        const dishTypeId = foundCategory ? foundCategory.dishTypeId : null;

        this.toEditDish = dish;
        this.formGroup.patchValue({
          productID: dish.dishId,
          productName: dish.dishName,
          category: dishTypeId,
          price: dish.dishPriceValue,
          description: dish.dishDescription,
          status: dish.dishStatus === 'ACTIVE',
          image: dish.dishImageUrl,
          discount: 100 - (dish.dishPriceValue / dish.dishSalePrice) * 100,
          sellingPrice: dish.dishSalePrice
        });
      }
    });

    this.formGroup.get('price')?.valueChanges.subscribe(() => this.calculateSellingPrice());
    this.formGroup.get('discount')?.valueChanges.subscribe(() => this.calculateSellingPrice());
  }

  ngAfterViewInit() {
    // new Swiper(this.paginationDynamicSliderContainer.nativeElement, {
    //   pagination: {
    //     el: '.swiper-pagination',
    //     dynamicBullets: true,
    //   },
    // });
  }

  formGroup!: FormGroup;
  productId!: any;
  @ViewChild('paginationDynamicSlider') paginationDynamicSliderContainer!: ElementRef;

  catOptions = [
    { label: "Fashion", value: "Fashion" },
    { label: "Fruits", value: "fruits" },
    { label: "Footwear", value: "Footwear" },
    { label: "Bags", value: "Bags" },
    { label: "Watch", value: "Watch" },
  ]

  brandOption = [
    { label: "Gucci", value: "Gucci" },
    { label: "Rolex", value: "Rolex" },
    { label: "Calvin Klein", value: "Calvin Klein" },
    { label: "Zara", value: "Zara" },
    { label: "Nike", value: "Nike" },
    { label: "Adidas", value: "Adidas" },
  ]

  sizeOptions = [
    { label: "XS", value: "XS" },
    { label: "S", value: "S" },
    { label: "M", value: "M" },
    { label: "L", value: "L" },
    { label: "XL", value: "XL" },
    { label: "2XL", value: "2XL" },
    { label: "2", value: "2" },
    { label: "3", value: "3" },
    { label: "4", value: "4" },
    { label: "5", value: "5" },
    { label: "6", value: "6" },
    { label: "7", value: "7" },
    { label: "8", value: "8" },
    { label: "9", value: "9" },
  ]

  colorOptions = [
    { label: "blue", value: "blue" },
    { label: "green", value: "green" },
    { label: "yellow", value: "yellow" },
    { label: "sky", value: "sky" },
    { label: "red", value: "red" },
    { label: "pink", value: "pink" },
    { label: "gray", value: "gray" },
    { label: "purple", value: "purple" },
  ]

  selectedSizes: string[] = [];
  selectedBrand: string[] = [];

  // Select All option
  selectAllOption = { label: "Select All", value: "selectAll" };

  calculateSellingPrice() {
    const price = parseFloat(this.formGroup.get('price')?.value || '0');
    const discount = parseFloat(this.formGroup.get('discount')?.value || '0');

    if (isNaN(price) || isNaN(discount) || discount <= 0) {
      this.formGroup.get('sellingPrice')?.setValue(0);
    } else {
      const sellingPrice = (price * (1 + discount / 100)).toFixed(0);
      this.formGroup.get('sellingPrice')?.setValue(sellingPrice);
    }
  }

  onFileSelected(event: Event, imageType: string): void {
    const input = event.target as HTMLInputElement;
    let selectedFiles: { [key: string]: File } = { };

    if (input.files && input.files[0]) {
      const file = input.files[0];
      selectedFiles[imageType] = file;

      const reader = new FileReader();
      reader.onload = () => {
        // if (imageType === 'image1') {
        //   this.formGroup.get('image1')?.setValue(reader.result as string);
        // } else if (imageType === 'image2') {
        //   this.formGroup.get('image2')?.setValue(reader.result as string);
        // } else
        if (imageType === 'image') {
          this.formGroup.get('image')?.setValue(reader.result as string);
        }
      };
      reader.readAsDataURL(file);
    }
  }

  onSelectionChange() {
    const allSelected = this.selectedSizes.includes(this.selectAllOption.value);

    if (allSelected) {
      // Select all options
      this.selectedSizes = this.sizeOptions.map(option => option.value);
    } else if (this.selectedSizes.length < this.sizeOptions.length) {
      // Remove Select All if not all items are selected
      this.selectedSizes = this.selectedSizes.filter(value => value !== this.selectAllOption.value);
    }
  }

  plusData(ctrl: string) {
    let control = this.formGroup.get(ctrl);
    if (control) {
      control.setValue(control.value + 1, { emitEvent: true });
    }
  }

  minusData(ctrl: string) {
    let control = this.formGroup.get(ctrl);
    if (control) {
      control.setValue(control.value - 1, { emitEvent: true });
    }
  }

  addOrEdit() {
    if (!this.formGroup.valid) {
      return
    }

    if (this.productId) {
      this.edit();
    } else {
      this.add();
    }
  }

  edit() {
    const formData = new FormData();

    if (this.formGroup.get('image')?.value != this.toEditDish?.dishImageUrl) {
      console.log("Image changed");
    }else {
      console.log("Image not changed");
    }

    const dishRequest = {
      dishId: this.formGroup.get('productID')?.value,
      dishName: this.formGroup.get('productName')?.value,
      dishDescription: this.formGroup.get('description')?.value,
      dishPriceValue: Number(this.formGroup.get('price')?.value),
      dishSalePrice: Number(this.formGroup.get('sellingPrice')?.value),
      dishStatus: this.formGroup.get('status')?.value ? 'ACTIVE' : 'INACTIVE',
      dishTypeId: this.formGroup.get('category')?.value,
      restaurantId: 751
    };

    // Đưa JSON request vào FormData
    formData.append('request', new Blob([JSON.stringify(dishRequest)], { type: 'application/json' }));

    // Đưa file ảnh vào
    const fileInput = document.getElementById('logo1') as HTMLInputElement;
    const imageFile = fileInput?.files?.[0];
    if (imageFile) {
      console.log("has image");
      formData.append('image', imageFile);
    } else {
      console.log("no image");
    }

    // Gửi HTTP POST
    this.http.post('http://localhost:8080/foodee/dish/update/v1.0', formData).subscribe({
      next: (res) => {
        console.log('Sửa món thành công', res);
        this.popupNoti.success("Sửa thông tin món thành công", "Thông báo");
        this.router.navigate(['/dashboard/apps-dish-management-list']);
      },
      error: (err) => {
        console.error('Lỗi tạo món', err);
      }
    });

    this.router.navigate([`/dashboard/apps-dish-management-list`]);
  }

  add() {
    //this.store.dispatch(addProduct({ product: this.formGroup.getRawValue() }));

    const formData = new FormData();

    const dishRequest = {
      dishId: this.formGroup.get('productID')?.value,
      dishName: this.formGroup.get('productName')?.value,
      dishDescription: this.formGroup.get('description')?.value,
      dishPriceValue: Number(this.formGroup.get('price')?.value),
      dishSalePrice: Number(this.formGroup.get('sellingPrice')?.value),
      dishStatus: this.formGroup.get('status')?.value ? 'ACTIVE' : 'INACTIVE',
      dishTypeId: this.formGroup.get('category')?.value,
      restaurantId: 751
    };

    // Đưa JSON request vào FormData
    formData.append('request', new Blob([JSON.stringify(dishRequest)], { type: 'application/json' }));

    // Đưa file ảnh vào
    const fileInput = document.getElementById('logo1') as HTMLInputElement;
    const imageFile = fileInput?.files?.[0];
    if (imageFile) {
      formData.append('image', imageFile);
    }

    // Gửi HTTP POST
    this.http.post('http://localhost:8080/foodee/dish/create/v1.0', formData).subscribe({
      next: (res) => {
        console.log('Tạo món thành công', res);
        this.popupNoti.success("Thêm món thành công <a href=\"https://example.com\" target=\"_blank\">tại đây</a>", "Thông báo");
        this.router.navigate(['/dashboard/apps-dish-management-list']);
      },
      error: (err) => {
        console.error('Lỗi tạo món', err);
      }
    });

    this.router.navigate([`/dashboard/apps-dish-management-list`]);
  }

  onDelete() {
    console.log("Delete ", this.productId);
    this.dishService.deleteDish(this.productId).subscribe({
      next: (res) => {
        console.log('Xóa thành công', res);
        this.popupNoti.success("Xóa thành công", "Thông báo");
        // Chỉ chuyển trang khi xóa thành công
        this.router.navigate([`/dashboard/apps-dish-management-list`]);
      },
      error: (err) => {
        console.error('Xóa thất bại', err);
        // Có thể show thông báo lỗi ở đây
      }
    });
  }

}
