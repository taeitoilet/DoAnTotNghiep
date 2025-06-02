import { Routes } from '@angular/router';
import { LayoutsComponent } from './layouts/layouts.component';
import { AuthAccountDeactivationBasicComponent } from './pages/Auth/auth-account-deactivation-basic/auth-account-deactivation-basic.component';
import { AuthForgotPasswordBasicComponent } from './pages/Auth/auth-forgot-password-basic/auth-forgot-password-basic.component';
import { AuthResetPasswordBasicComponent } from './pages/Auth/auth-reset-password-basic/auth-reset-password-basic.component';
import { AuthSigninBasicComponent } from './pages/Auth/auth-signin-basic/auth-signin-basic.component';
import { AuthSignupBasicComponent } from './pages/Auth/auth-signup-basic/auth-signup-basic.component';
import { AuthTwoStepVerificationBasicComponent } from './pages/Auth/auth-two-step-verification-basic/auth-two-step-verification-basic.component';
import { AuthSuccessfulPasswordBasicComponent } from './pages/Auth/auth-successful-password-basic/auth-successful-password-basic.component';
import { Pages500Component } from './pages/OtherPages/pages-500/pages-500.component';
import { Pages404Component } from './pages/OtherPages/pages-404/pages-404.component';
import { PagesMaintenanceComponent } from './pages/OtherPages/pages-maintenance/pages-maintenance.component';
import { PagesComingSoonComponent } from './pages/OtherPages/pages-coming-soon/pages-coming-soon.component';
import { PagesContactUsComponent } from './pages/OtherPages/pages-contact-us/pages-contact-us.component';
import { PagesContactUsFiveComponent } from './pages/OtherPages/pages-contact-us-five/pages-contact-us-five.component';
import { PagesContactUsFourComponent } from './pages/OtherPages/pages-contact-us-four/pages-contact-us-four.component';
import { PagesContactUsThreeComponent } from './pages/OtherPages/pages-contact-us-three/pages-contact-us-three.component';
import { PagesContactUsTwoComponent } from './pages/OtherPages/pages-contact-us-two/pages-contact-us-two.component';
import { LandingEcommerceComponent } from './pages/landing/landing-ecommerce/landing-ecommerce.component';
import { AppsEcommerceProductsListComponent } from './pages/Apps/Ecommerce/Products/components/apps-ecommerce-products-list/apps-ecommerce-products-list.component';
import { AppsEcommerceCustomerUserComponent } from './pages/Apps/Ecommerce/Customers/apps-ecommerce-customer-user/apps-ecommerce-customer-user.component';
import { AgGridProductsListComponent } from './pages/Apps/Products/ag-grid-products-list/ag-grid-products-list.component';
import {AuthenticateComponent} from "./module/authenticate/authenticate/authenticate.component";
import {authGuard} from "./guards/auth.guard";
import {UnauthorizedComponent} from "./pages/Auth/unauthorized/unauthorized.component";
import {
  AppsEcommerceRestaurantOverviewComponent
} from "./pages/Apps/Ecommerce/Products/components/apps-ecommerce-restaurant-overview/apps-ecommerce-restaurant-overview.component";

export const routes: Routes = [
  {
    path: '',
    component: AuthSigninBasicComponent,
  },
  {
    path: 'dashboard',
    component: LayoutsComponent,
    // canActivate: [authGuard],
    // data: { roles: ['ROLE_USER', 'ROLE_ADMIN'] },
    loadChildren: () =>
      import('./layouts/layout.route').then((mod) => mod.PAGE_ROUTES),
  },

  //Auth
  { path: 'auth-account-deactivation-basic', component: AuthAccountDeactivationBasicComponent, data: { title: 'AccountDeactivation' } },
  { path: 'unauthorized', component: UnauthorizedComponent, data: { title: 'Unauthorized' } },
  { path: 'authenticate', component: AuthenticateComponent, data: { title: 'Authenticate' } },
  { path: 'auth-forgot-password-basic', component: AuthForgotPasswordBasicComponent, data: { title: 'ForgotPassword' } },
  { path: 'auth-reset-password-basic', component: AuthResetPasswordBasicComponent, data: { title: 'ResetPassword' } },
  { path: 'auth-signin-basic', component: AuthSigninBasicComponent, data: { title: 'signin' } },
  { path: 'auth-signup-basic', component: AuthSignupBasicComponent, data: { title: 'signup' } },
  { path: 'auth-two-step-verification-basic', component: AuthTwoStepVerificationBasicComponent, data: { title: 'TwoStepVerification' } },
  { path: 'auth-successful-password-basic', component: AuthSuccessfulPasswordBasicComponent, data: { title: 'SuccessfulPassword' } },

  //Other Pages
  { path: 'pages-contact-us', component: PagesContactUsComponent, data: { title: 'ContactUs' } },
  { path: 'pages-coming-soon', component: PagesComingSoonComponent, data: { title: 'comingSoon' } },
  { path: 'pages-maintenance', component: PagesMaintenanceComponent, data: { title: 'maintenance' } },
  { path: 'pages-404', component: Pages404Component, data: { title: '404' } },
  { path: 'pages-500', component: Pages500Component, data: { title: '500' } },
  { path: 'pages-contact-us-five', component: PagesContactUsFiveComponent, data: { title: 'ContactUs' } },
  { path: 'pages-contact-us-four', component: PagesContactUsFourComponent, data: { title: 'ContactUs' } },
  { path: 'pages-contact-us-three', component: PagesContactUsThreeComponent, data: { title: 'ContactUs' } },
  { path: 'pages-contact-us-two', component: PagesContactUsTwoComponent, data: { title: 'ContactUs' } },

  //landing
  { path: 'landing-ecommerce', component: LandingEcommerceComponent, data: { title: 'ecommerce' } },

  // Ecommerce page
  { path: 'apps-ecommerce-products-list', component: AppsEcommerceProductsListComponent, data: { title: 'ProductsList' } },
  { path: 'ag-grid', component: AgGridProductsListComponent, data: { title: 'grid' } },
  { path: 'apps-ecommerce-customer-user', component: AppsEcommerceCustomerUserComponent, data: { title: 'CustomerUser' } },

];
