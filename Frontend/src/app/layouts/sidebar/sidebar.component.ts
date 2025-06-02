import {Component, HostListener, OnInit} from '@angular/core';
import { DomixDropdownModule } from '../../module/domix dropdown/domix-dropdown.module';
import { CommonModule } from '@angular/common';
import { LucideAngularModule } from 'lucide-angular';
import { SimplebarAngularModule } from 'simplebar-angular';
import { menu } from '../../Data/menu';
import { SearchService } from '../../service/search.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ActivatedRoute, NavigationEnd, Router, RouterLink } from '@angular/router';
import { LayoutSettingService } from '../layout-setting.service';
import { FormsModule } from '@angular/forms';
import {AuthenticateService} from "../../Core/service/authenticate-service/authenticate.service";
import {LocalStorageService} from "../../service/local-storage.service";
import {User} from "../../Data/models";
import {UserService} from "../../Core/service/user-api-service/user.service";
import {AuthService} from "../../service/auth-service/auth.service";

@Component({
  selector: 'app-sidebar',
  imports: [
    DomixDropdownModule,
    CommonModule,
    LucideAngularModule,
    FormsModule,
    SimplebarAngularModule,
    RouterLink,
    TranslateModule
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit{
  public dropdownStates: boolean[] = [];
  public dropdownChildStates: boolean[][] = [];
  isSidebarOpen: boolean = false;
  isMobile: boolean = window.innerWidth < 1024;
  scrolled = false;
  menu = menu;
  filteredMenu = menu;
  originalMenu = menu;
  activeUrl: string = window.location.pathname;
  languageData: any = {};
  allowToClose: boolean = false;
  activeRoute: string = '';
  isLayoutHorizontal = false;
  isdataSidebarOpen = false;
  user: User | null = null;
  role : string[] = []

  constructor(
    private searchService: SearchService,
    private translate: TranslateService,
    private http: HttpClient,
    private router: Router,
    private settingService: LayoutSettingService,
    protected authenticateService: AuthenticateService,
    protected localStorageService: LocalStorageService,
    private authService: AuthService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.getMyInfo().subscribe((data: any) => {
      if (data) {
        this.user = data.data;
      }
    })

    this.settingService.sidebarState$.subscribe((state) => {
      this.isSidebarOpen = state;
    });

    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd && this.isMobile) {
        this.isSidebarOpen = false;
        this.settingService.toggleSidebar(); // Ensure sidebar state updates correctly
      }
    });

    this.settingService.settings$.subscribe((settings) => {
      this.isLayoutHorizontal = settings.layout === 'horizontal' ? true : false;
      this.isdataSidebarOpen = settings.sidebarSize === 'small' ? true : false;
    });

    this.activeRoute = this.router.url;

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.activeRoute = this.router.url;
      }
    });

    this.fetchMenu();

    // Initialize dropdown states
    this.dropdownStates = new Array(this.menu.length).fill(false);
    this.dropdownChildStates = this.menu.map((item) =>
      new Array(item.children.length).fill(false)
    );

    this.searchService.searchQuery$.subscribe((query) => {
      this.filterMenu(query);
    });
    this.role = this.authService.getRoles()
  }
  setSidebar() {
    this.settingService.toggleSidebar();
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isMobile = event.target.innerWidth < 1024;
  }

  ngOnDestroy(): void { }

  isActive(link: any): boolean {
    return this.activeRoute === link;
  }

  hasActiveChild(item: any): boolean {
    if (!item.children || item.children.length === 0) {
      return false;
    }

    return item.children.some((child: any) => this.isActive(child.link) || this.hasActiveChild(child));
  }

  shouldOpenDropdown(children: any[]): boolean {
    return children.some(child => this.isActive(child.link));
  }

  filterMenu(query: string) {
    this.filteredMenu = this.originalMenu.filter((item) =>
      item.title.toLowerCase().includes(query.toLowerCase())
    );
  }

  toggleDropdown(index: number): void {
    this.dropdownStates[index] = !this.dropdownStates[index];
  }

  // Close other dropdowns if needed
  closeDropdown(index: number): void {
    this.dropdownStates[index] = false;
  }

  toggleDropdownChild(parentIndex: number, childIndex: number): void {
    if (!this.dropdownChildStates[parentIndex]) {
      this.dropdownChildStates[parentIndex] = [];
    }
    this.dropdownChildStates[parentIndex][childIndex] =
      !this.dropdownChildStates[parentIndex][childIndex];
  }

  closeDropdownChild(parentIndex: number, childIndex: number): void {
    if (this.dropdownChildStates[parentIndex]) {
      this.dropdownChildStates[parentIndex][childIndex] = false;
    }
  }

  // filterData(term: string): void {
  //   if (term) {
  //     this.filteredMenu = this.menu.filter(
  //       (item) =>
  //         (item.title &&
  //           item.title.toLowerCase().includes(term.toLowerCase())) ||
  //         (item.children &&
  //           item.children.some((child) =>
  //             child.title.toLowerCase().includes(term.toLowerCase())
  //           ))
  //     );
  //   } else {
  //     this.filteredMenu = this.menu;
  //   }
  //   this.fetchMenu();
  // }

  chunkArray(array: any[], chunkSize: number): any[][] {
    const chunks: any[][] = [];
    for (let i = 0; i < array.length; i += chunkSize) {
      chunks.push(array.slice(i, i + chunkSize));
    }
    return chunks;
  }

  fetchMenu(): void {
    setTimeout(() => {
      // Initialize or update icons if necessary
    }, 0);
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
  isExternalLink(link: string): boolean {
    return link.startsWith('http');
  }

  hasRole(itemRoles: string[]): boolean {
    return itemRoles.some(role => this.role.includes(role));
  }
  protected readonly localStorage = localStorage;
}

