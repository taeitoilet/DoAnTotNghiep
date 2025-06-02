import { CommonModule } from '@angular/common';
import {Component, HostListener, inject, OnDestroy, OnInit} from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { DomixDropdownModule } from '../../module/domix dropdown/domix-dropdown.module';
import { ToolsAppsModalComponent } from './modal/tools-apps-modal/tools-apps-modal.component';
import { ModalService } from '../../Core/service/modal/modal.service';
import { SettingsModalComponent } from './modal/settings-modal/settings-modal.component';
import { SimplebarAngularModule } from 'simplebar-angular';
import { SearchService } from '../../service/search.service';
import { LayoutSettingService } from '../layout-setting.service';
import { LanguageService } from '../../Core/service/language.service';
import { ProductDrawerComponent } from './product-drawer/product-drawer.component';
import { DrawerConfig, DrawerService } from '../../Core/service/Drawer/drawer.service';
import {Router, RouterLink} from '@angular/router';
import {SseService} from "../../service/sse.service";
import {TranslateModule} from "@ngx-translate/core";
import { ChangeDetectorRef } from '@angular/core';
import {LocalStorageService} from "../../service/local-storage.service";
import {AuthService} from "../../service/auth-service/auth.service";
import {NotificationService} from "../../Core/service/noti-service/notification.service";
import {PopupService} from "ag-grid-community";
import {PopupNotiService} from "../../Core/service/popup-noti-service/popup-noti.service";
import * as ProductSelectors from "../../pages/Apps/Ecommerce/Products/store/selectors/product.selectors";
import {Store} from "@ngrx/store";
import {UserService} from "../../Core/service/user-api-service/user.service";
import {User} from "../../Data/models";
import {AuthenticateService} from "../../Core/service/authenticate-service/authenticate.service";

interface Language {
  id: string;
  flag: string;
  name: string;
};

class notification {
  notificationId: string = '';
  notificationTitle: string = '';
  notificationMessage: string = '';
  notificationIsRead: boolean = false;
  notificationCreatedAt: string = '';
  notificationType: string = '';
  orderId : number = 0
  }

@Component({
  selector: 'app-navbar',
  imports: [
    CommonModule,
    RouterLink,
    LucideAngularModule,
    DomixDropdownModule,
    SimplebarAngularModule,
    TranslateModule,
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit, OnDestroy{
  store = inject(Store);
  scrolled: boolean = false;
  addCartProductIds: string[] = [];
  messages: string[] = [];
  userId: string = localStorage.getItem('userId') || ''; // Ví dụ userId
  private sseSubscription: any;
  user: User | null = null;
  role : string[] = []
  notifications: notification[] = [];
  unreadCount: number = 0;

  languageData: any = {};
  currantLayout!: string;
  settings!: {
    mode: string;
    sidebarSize: string;
  }

  constructor(
    private modalService: ModalService,
    private searchService: SearchService,
    private settingService: LayoutSettingService,
    public languageService: LanguageService,
    public drawerService: DrawerService,
    private sseService: SseService,
    private cdr: ChangeDetectorRef,
    protected localStorageService: LocalStorageService,
    protected authenticateService: AuthenticateService,
    private authService: AuthService,
    private notificationService: NotificationService,
    private popupService: PopupNotiService,
    private userService: UserService,
    private router: Router
  ) {
    this.settingService.settings$.subscribe((settings) => {
      this.settings = settings;
    });
    this.store.select(ProductSelectors.selectCartItems).subscribe((product) => {
      this.addCartProductIds = product.map((x) => x.pId);
      console.log(this.addCartProductIds);
    });
  }

  ngOnInit(): void {
    this.userService.getMyInfo().subscribe((data: any) => {
      if (data) {
        this.user = data.data;
      }
    })

    // Kết nối đến SSE khi component được khởi tạo
    this.authService.getUserId(this.localStorageService.getToken() || '');
    this.userId = localStorage.getItem('userId') || '';
    this.loadNotifications()
  }

  loadNotifications() {
    this.sseService.connectToSSE(this.userId).subscribe(
      (event: any) => {
        // Xử lý thông điệp nhận được từ SSE
        console.log('Received message:', event);
        this.notifications.push(event);
        this.unreadCount = this.notifications.filter((notification) => !notification.notificationIsRead).length;
        this.cdr.detectChanges();
      },
      (error) => {
        console.error('Error in SSE connection:', error);
      }
    );
    this.role = this.authService.getRoles()
  }

  ngOnDestroy() {
    // Đảm bảo đóng kết nối SSE khi component bị hủy
    if (this.sseSubscription) {
      this.sseSubscription.unsubscribe();
      this.sseService.disconnect();
    }
  }

  calculateTimeAgo(dateString: string): string {
    const notificationDate = new Date(dateString);
    const currentDate = new Date();
    const diffInSeconds = Math.floor((currentDate.getTime() - notificationDate.getTime()) / 1000);

    if (diffInSeconds < 60) {
      return `${diffInSeconds} seconds ago`;
    }

    const diffInMinutes = Math.floor(diffInSeconds / 60);
    if (diffInMinutes < 60) {
      return `${diffInMinutes} minutes ago`;
    }

    const diffInHours = Math.floor(diffInMinutes / 60);
    if (diffInHours < 24) {
      return `${diffInHours} hours ago`;
    }

    const diffInDays = Math.floor(diffInHours / 24);
    return `${diffInDays} days ago`;
  }

  setSidebar() {
    if (this.isMobile) {
      // On mobile always set sidebar size to large
      this.settingService.updateSettings({ sidebarSize: 'large' });
    } else {
      // On desktop toggle between small and large
      this.settingService.updateSettings({ sidebarSize: this.settings.sidebarSize === 'small' ? 'large' : 'small' });
    }
    this.settingService.toggleSidebar();
  }

  isMobile: boolean = window.innerWidth < 1024;

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isMobile = event.target.innerWidth < 1024;
  }

  @HostListener('window:scroll', [])
  onWindowScroll(): void {
    const scrollTop =
      window.pageYOffset ||
      document.documentElement.scrollTop ||
      document.body.scrollTop ||
      0;
    this.scrolled = scrollTop > 50;
  }

  onSearchInput(event: Event) {
    const input = event.target as HTMLInputElement;
    this.searchService.setSearchQuery(input.value);
  }

  openToolsModal() {
    this.modalService.open(ToolsAppsModalComponent);
  }
  openSettingsModal() {
    this.modalService.open(SettingsModalComponent);
  }
  toggleDarkMode() {
    const mode = this.settings.mode === 'dark' ? 'light' : 'dark'

    this.settingService.updateSettings({ mode: mode });
  }

  setLanguage(lang: string) {
    this.languageService.setLanguage(lang);
  }

  openDrawer(po: 'end' | 'start' | 'top' | 'bottom' = 'bottom') {
    const config: DrawerConfig = {
      title: 'My Cart',
      content: '',
      position: po,
    };
    this.drawerService.open(ProductDrawerComponent, config);
  }

  markAllAsRead() {
    const notificationIds = this.notifications.map((notification) => (Number)(notification.notificationId));
    this.notificationService.markAsRead(notificationIds).subscribe(
      () => {
        this.notifications = [];
        this.loadNotifications();
        this.unreadCount = this.notifications.filter((notification) => !notification.notificationIsRead).length;
      },
      (error) => {
        this.popupService.error('Error marking notifications as read');
        console.error('Error marking notifications as read:', error);
      }
    )
  }

  markAsRead(notificationId: any) {
    this.notificationService.markAsRead([Number(notificationId)]).subscribe(
      () => {
        this.notifications = [];
        this.loadNotifications();
        this.unreadCount = this.notifications.filter((notification) => !notification.notificationIsRead).length;
      },
      (error) => {
        this.popupService.error('Error marking notification as read');
        console.error('Error marking notification as read:', error);
      }
    )
  }
  goToDetail(id:number,title : string): void {
    if(title === 'Đơn đặt bàn mới'){
      this.router.navigate(['dashboard/apps-ecommerce-booking-list']);
    }else{
      this.router.navigate(['dashboard/apps-ecommerce-orders-overview',id]);
    }
  }
}
