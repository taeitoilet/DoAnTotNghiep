import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { LayoutSettingService } from './layouts/layout-setting.service';
import { addToCart, addToWishlist } from './pages/Apps/Ecommerce/Products/store/actions/product.actions';
import { Store } from '@ngrx/store';
import { LanguageService } from './Core/service/language.service';
import { TitleService } from './service/title.service';
import { filter } from 'rxjs';
import { ModalService } from './Core/service/modal/modal.service';
import { SettingsModalComponent } from './layouts/navbar/modal/settings-modal/settings-modal.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{
  title = 'domiex';
  userData = {
    name: 'Sophia Mia',
    image: 'assets/images/avatar/user-17.png',
  };

  currentLocation: { lat: number, lng: number } = { lat: 0, lng: 0 };
  errorMessage: string = '';

  getCurrentLocation() {
    // Kiểm tra xem trình duyệt có hỗ trợ Geolocation API hay không
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          console.log('Accuracy:', position.coords.accuracy); // Kiểm tra độ chính xác
          if (position.coords.accuracy <= 100) {
            this.currentLocation = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };
          } else {
            console.error('Độ chính xác không đủ tốt:', position.coords.accuracy);
          }
          localStorage.setItem('latitude', JSON.stringify(this.currentLocation.lat));
          localStorage.setItem('longitude', JSON.stringify(this.currentLocation.lng));
          console.log("Vị trí hiện tại: ", this.currentLocation);
        },
        (error) => {
          // Nếu người dùng từ chối quyền truy cập hoặc có lỗi
          this.errorMessage = 'Không thể truy cập vị trí của bạn. Vui lòng cấp quyền truy cập!';
          localStorage.setItem('latitude', '21.0492744');
          localStorage.setItem('longitude', '105.7324997');
          console.error(error);
        },
        {
          // enableHighAccuracy: true,  // Yêu cầu độ chính xác cao
          // timeout: 5000  // Thời gian chờ tối đa là 10 giây
        }
      );
    } else {
      this.errorMessage = 'Trình duyệt của bạn không hỗ trợ xác định vị trí hiện tại!';
    }
  }

  constructor(private settingService: LayoutSettingService, private store: Store, private translateS: LanguageService, private router: Router,
    private titleService: TitleService,
    private modalService: ModalService,
    private activatedRoute: ActivatedRoute) {
    this.settingService.settings$.subscribe((settings) => {
      this.settingService.handleSettingsChange(settings);
    });
  }

  defaultAddToCartDatas = [

  ]

  defaultWishListDatas = [

  ]

  ngOnInit() {
    this.addToCart();
    this.addToWishList();
    //this.modalService.open(SettingsModalComponent);
    this.translateS.setLanguage(this.translateS.getStoredSettings());
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.setTitle();
    });
    this.getCurrentLocation();
  }

  private setTitle() {
    let route = this.activatedRoute.root;

    // Traverse up the route tree to find route data (title)
    while (route.firstChild) {
      route = route.firstChild;
    }

    // Access the route data (title) and set it using TitleService
    if (route.snapshot.data['title']) {
      this.titleService.setTitle(route.snapshot.data['title']);
    }
  }

  addToCart() {
    this.defaultAddToCartDatas.forEach(pData => {
      this.store.dispatch(addToCart({ product: pData }));
    });
  }

  addToWishList() {
    this.defaultWishListDatas.forEach(wishlistData => {
      this.store.dispatch(addToWishlist({ product: wishlistData }));
    });
  }
}
