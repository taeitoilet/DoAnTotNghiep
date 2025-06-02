import { Routes } from '@angular/router';
import { IndexComponent } from '../pages/dashboards/index/index.component';
import { PagesAccountSecurityComponent } from '../pages/OtherPages/pages-account-security/pages-account-security.component';
import { PagesAccountSettingsComponent } from '../pages/OtherPages/pages-account-settings/pages-account-settings.component';
import { PagesAccountStatementsComponent } from '../pages/OtherPages/pages-account-statements/pages-account-statements.component';
import { PagesFaqComponent } from '../pages/OtherPages/pages-faq/pages-faq.component';
import { PagesHelpCenterComponent } from '../pages/OtherPages/pages-help-center/pages-help-center.component';
import { PagesLicensesComponent } from '../pages/OtherPages/pages-licenses/pages-licenses.component';
import { PagesPricingAdminComponent } from '../pages/OtherPages/pages-pricing-admin/pages-pricing-admin.component';
import { PagesPricingComponent } from '../pages/OtherPages/pages-pricing/pages-pricing.component';
import { PagesPrivacyPolicyComponent } from '../pages/OtherPages/pages-privacy-policy/pages-privacy-policy.component';
import { PagesStarterComponent } from '../pages/OtherPages/pages-starter/pages-starter.component';
import { PagesUserActivityComponent } from '../pages/OtherPages/pages-user-activity/pages-user-activity.component';
import { PagesUserDocumentsComponent } from '../pages/OtherPages/pages-user-documents/pages-user-documents.component';
import { PagesUserFollowersComponent } from '../pages/OtherPages/pages-user-followers/pages-user-followers.component';
import { PagesUserNotesComponent } from '../pages/OtherPages/pages-user-notes/pages-user-notes.component';
import { PagesUserProjectsComponent } from '../pages/OtherPages/pages-user-projects/pages-user-projects.component';
import { PagesUserComponent } from '../pages/OtherPages/pages-user/pages-user.component';
import { PagesAccountNotificationComponent } from '../pages/OtherPages/pages-account-notification/pages-account-notification.component';
import { PagesAccountLogsComponent } from '../pages/OtherPages/pages-account-logs/pages-account-logs.component';
import { PagesAccountBillingPlanComponent } from '../pages/OtherPages/pages-account-billing-plan/pages-account-billing-plan.component';
import { AppsEcommerceCreateProductsComponent } from '../pages/Apps/Ecommerce/Products/components/apps-ecommerce-create-products/apps-ecommerce-create-products.component';
import { AppsEcommerceProductOverviewComponent } from '../pages/Apps/Ecommerce/Products/components/apps-ecommerce-product-overview/apps-ecommerce-product-overview.component';
import { AppsEcommerceCategoryComponent } from '../pages/Apps/Ecommerce/Products/components/apps-ecommerce-category/apps-ecommerce-category.component';
import { AppsEcommerceOrdersListComponent } from '../pages/Apps/Ecommerce/Orders/apps-ecommerce-orders-list/apps-ecommerce-orders-list.component';
import { AppsEcommerceOrdersOverviewComponent } from '../pages/Apps/Ecommerce/Orders/apps-ecommerce-orders-overview/apps-ecommerce-orders-overview.component';
import { AppsEcommerceOrdersTrackComponent } from '../pages/Apps/Ecommerce/Orders/apps-ecommerce-orders-track/apps-ecommerce-orders-track.component';
import { AppsEcommerceCustomerListComponent } from '../pages/Apps/Ecommerce/Customers/apps-ecommerce-customer-list/apps-ecommerce-customer-list.component';
import { AppsEcommerceShopCartComponent } from '../pages/Apps/Ecommerce/Products/components/apps-ecommerce-shop-cart/apps-ecommerce-shop-cart.component';
import { AppsEcommerceManageReviewsComponent } from '../pages/Apps/Ecommerce/apps-ecommerce-manage-reviews/apps-ecommerce-manage-reviews.component';
import { AppsInvoiceCreateComponent } from '../pages/Apps/Invoice/components/apps-invoice-create/apps-invoice-create.component';
import { AppsInvoiceGridComponent } from '../pages/Apps/Invoice/components/apps-invoice-grid/apps-invoice-grid.component';
import { AppsInvoiceListComponent } from '../pages/Apps/Invoice/components/apps-invoice-list/apps-invoice-list.component';
import { AppsInvoiceOverview1Component } from '../pages/Apps/Invoice/components/apps-invoice-overview-1/apps-invoice-overview-1.component';
import { AppsInvoiceOverview2Component } from '../pages/Apps/Invoice/components/apps-invoice-overview-2/apps-invoice-overview-2.component';
import { AppsEcommerceProductsGridComponent } from '../pages/Apps/Ecommerce/Products/components/apps-ecommerce-products-grid/apps-ecommerce-products-grid.component';
import { AppsEcommerceWishlistComponent } from '../pages/Apps/Ecommerce/Products/components/apps-ecommerce-wishlist/apps-ecommerce-wishlist.component';
import { AppsEcommerceCheckoutComponent } from '../pages/Apps/Ecommerce/Products/components/apps-ecommerce-checkout/apps-ecommerce-checkout.component';
import { MapsVectorComponent } from '../pages/Maps/maps-vector/maps-vector.component';
import { MapsGoogleComponent } from '../pages/Maps/maps-google/maps-google.component';
import { PaymentComponent } from '../pages/Apps/Ecommerce/Products/components/payment/payment.component';
import {StaffManagementComponent} from "../pages/dashboards/staff-management/staff-management.component";
import {ResManagementComponent} from "../pages/dashboards/res-management/res-management.component";
import {PendingResComponent} from "../pages/dashboards/pending-res/pending-res.component";
import {AuthenticateComponent} from "../module/authenticate/authenticate/authenticate.component";
import {
  AppsEcommerceRestaurantGridComponent
} from "../pages/Apps/Ecommerce/Products/components/apps-ecommerce-restaurant-grid/apps-ecommerce-restaurant-grid.component";
import {
  AppsEcommerceRestaurantOverviewComponent
} from "../pages/Apps/Ecommerce/Products/components/apps-ecommerce-restaurant-overview/apps-ecommerce-restaurant-overview.component";
import {
  AppsEcommerceDishOverviewComponent
} from "../pages/Apps/Ecommerce/Products/components/apps-ecommerce-dish-overview/apps-ecommerce-dish-overview.component";
import {DishManagementComponent} from "../pages/dashboards/đish-management/dish-management/dish-management.component";
import {DishCreateComponent} from "../pages/dashboards/đish-management/dish-create/dish-create.component";
import {
  AppsEcommerceBookingListComponent
} from "../pages/Apps/Ecommerce/Orders/apps-ecommerce-booking-list/apps-ecommerce-booking-list.component";
import {authGuard} from "../guards/auth.guard";

export const PAGE_ROUTES: Routes = [
  {
    path: 'apps-staff-list',
    component: StaffManagementComponent,
    data: { title: 'staff-list', roles: ['ROLE_ADMIN']},
    canActivate: [authGuard],
  },

  {
    path: 'apps-res-list',
    component: ResManagementComponent,
    canActivate: [authGuard],
    data: { title: 'res-list', roles: ['ROLE_ADMIN']}
  },

  {
    path: 'apps-pending-res-list',
    component: PendingResComponent,
    data: { title: 'pending-res-list', roles: ['ROLE_ADMIN']},
    canActivate: [authGuard]
  },

  {
    path: 'apps-dish-management-list',
    component: DishManagementComponent,
    data: { title: 'dish-management-list', roles: ['ROLE_ADMIN', 'ROLE_RESTAURANT']},
    canActivate: [authGuard],
  },

  {
    path: 'apps-dish-create',
    component: DishCreateComponent, data: { title: 'dish-create', roles: ['ROLE_ADMIN', 'ROLE_RESTAURANT'] },
    canActivate: [authGuard]
  },

  {
    path: 'apps-dish-edit/:productId',
    component: DishCreateComponent, data: { title: 'dish-edit', roles: ['ROLE_ADMIN', 'ROLE_RESTAURANT']},
    canActivate: [authGuard]
  },

  //Dashboard
  { path: '', component: IndexComponent, data: { title: 'Ecommerce' } },
  { path: 'dashboard', component: IndexComponent, data: { title: 'Ecommerce' } },

  //other Pages
  { path: 'pages-account-security', component: PagesAccountSecurityComponent, data: { title: 'security' } },
  { path: 'pages-account-settings', component: PagesAccountSettingsComponent, data: { title: 'settings' } },
  {
    path: 'pages-account-statements',
    component: PagesAccountStatementsComponent, data: { title: 'statements' }
  },
  { path: 'pages-faq', component: PagesFaqComponent, data: { title: 'faq' } },
  { path: 'pages-help-center', component: PagesHelpCenterComponent, data: { title: 'helpCenter' } },
  { path: 'pages-licenses', component: PagesLicensesComponent, data: { title: 'licenses' } },
  { path: 'pages-pricing-admin', component: PagesPricingAdminComponent, data: { title: 'pricing' } },
  { path: 'pages-pricing', component: PagesPricingComponent, data: { title: 'pricing' } },
  { path: 'pages-privacy-policy', component: PagesPrivacyPolicyComponent, data: { title: 'privacy' } },
  { path: 'pages-starter', component: PagesStarterComponent, data: { title: 'starter' } },
  { path: 'pages-user-activity', component: PagesUserActivityComponent, data: { title: 'activity' } },
  { path: 'pages-user-documents', component: PagesUserDocumentsComponent, data: { title: 'documents' } },
  { path: 'pages-user-followers', component: PagesUserFollowersComponent, data: { title: 'followers' } },
  { path: 'pages-user-notes', component: PagesUserNotesComponent, data: { title: 'notes' } },
  { path: 'pages-user-projects', component: PagesUserProjectsComponent, data: { title: 'projects' } },
  { path: 'pages-user', component: PagesUserComponent, data: { title: 'pagesUser' } },
  {
    path: 'pages-account-notification',
    component: PagesAccountNotificationComponent, data: { title: 'notification' }
  },
  {
    path: 'pages-account-logs',
    component: PagesAccountLogsComponent,
    data: { title: 'logs' }
  },
  {
    path: 'pages-account-billing-plan',
    component: PagesAccountBillingPlanComponent,
    data: { title: 'plan' }
  },

  //maps
  {
    path: 'maps-vector',
    component: MapsVectorComponent,
    data: { title: 'vector' }
  },
  {
    path: 'maps-google',
    component: MapsGoogleComponent,
    data: { title: 'google' }
  },

  {
    path: 'apps-invoice-create',
    component: AppsInvoiceCreateComponent,
    data: { title: 'create' }
  },
  {
    path: 'apps-invoice-create/:id',
    component: AppsInvoiceCreateComponent,
    data: { title: 'create' }
  },
  {
    path: 'apps-invoice-grid',
    component: AppsInvoiceGridComponent,
    data: { title: 'grid' }
  },
  {
    path: 'apps-invoice-list',
    component: AppsInvoiceListComponent,
    data: { title: 'list' }
  },
  {
    path: 'apps-invoice-overview-1',
    component: AppsInvoiceOverview1Component,
    data: { title: 'overview-1' }
  },
  {
    path: 'apps-invoice-overview-2',
    component: AppsInvoiceOverview2Component,
    data: { title: 'overview-2' }
  },
  {
    path: 'apps-ecommerce-create-products',
    component: AppsEcommerceCreateProductsComponent, data: { title: 'products' }
  },
  {
    path: 'apps-ecommerce-create-products/:productId',
    component: AppsEcommerceCreateProductsComponent, data: { title: 'products' }
  },
  {
    path: 'apps-ecommerce-product-overview/:productId',
    component: AppsEcommerceProductOverviewComponent, data: { title: 'overview' }
  },
  {
    path: 'apps-ecommerce-product-overview',
    component: AppsEcommerceProductOverviewComponent, data: { title: 'overview' }
  },
  {
    path: 'apps-ecommerce-category',
    component: AppsEcommerceCategoryComponent, data: { title: 'category' }
  },
  {
    path: 'apps-ecommerce-orders-list',
    component: AppsEcommerceOrdersListComponent, data: { title: 'orders-list' }
  },
  {
    path: 'apps-ecommerce-orders-track',
    component: AppsEcommerceOrdersTrackComponent, data: { title: 'orders-track' }
  },
  {
    path: 'apps-ecommerce-orders-overview/:id',
    component: AppsEcommerceOrdersOverviewComponent, data: { title: 'orders-overview' }
  },
  {
    path: 'apps-ecommerce-customer-list',
    component: AppsEcommerceCustomerListComponent, data: { title: 'customer-list' }
  },
  {
    path: 'apps-ecommerce-shop-cart',
    component: AppsEcommerceShopCartComponent, data: { title: 'shop-cart' }
  },
  {
    path: 'apps-ecommerce-manage-reviews',
    component: AppsEcommerceManageReviewsComponent, data: { title: 'manage-reviews' }
  },
  {
    path: 'apps-ecommerce-products-grid',
    component: AppsEcommerceProductsGridComponent, data: { title: 'products-grid' }
  },
  {
    path: 'apps-ecommerce-restaurant-grid',
    component: AppsEcommerceRestaurantGridComponent, data: { title: 'restaurant-grid' }
  },
  {
    path: 'apps-ecommerce-wishlist',
    component: AppsEcommerceWishlistComponent, data: { title: 'wishlist' }
  },
  {
    path: 'apps-ecommerce-checkout',
    component: AppsEcommerceCheckoutComponent, data: { title: 'checkout' }
  },
  {
    path: 'apps-ecommerce-booking-list',
    component: AppsEcommerceBookingListComponent, data: { title: 'booking' }
  },
  {
    path: 'apps-ecommerce-payment',
    component: PaymentComponent, data: { title: 'payment' }
  },
  {
    path: 'apps-ecommerce-restaurant-overview/:id',
    component: AppsEcommerceRestaurantOverviewComponent,
    data: {title:'RestaurantDetail'}
  },
  {
    path: 'apps-ecommerce-dish-overview/:id',
    component: AppsEcommerceDishOverviewComponent,
    data: {title:'dish'}
  }
];
