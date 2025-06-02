import { CommonModule } from '@angular/common';
import {Component, OnInit} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { LucideAngularModule } from 'lucide-angular';
import {AuthenticateService} from "../../../Core/service/authenticate-service/authenticate.service";
import {LocalStorageService} from "../../../service/local-storage.service";
import {AuthService} from "../../../service/auth-service/auth.service";
import {PopupNotiService} from "../../../Core/service/popup-noti-service/popup-noti.service";

@Component({
    selector: 'app-auth-signin-basic',
    imports: [ReactiveFormsModule, CommonModule, LucideAngularModule, RouterLink],
    templateUrl: './auth-signin-basic.component.html',
    styleUrl: './auth-signin-basic.component.scss'
})
export class AuthSigninBasicComponent implements OnInit {
  signInForm: FormGroup;
  authenticated = false;
  showPassword = false;
  role : string[] = []
  alert = {
    isVisible: false,
    type: '',
    message: '',
  };

  private predefinedEmail = 'admin';
  private predefinedPassword = 'admin';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authenticateService: AuthenticateService,
    private localStorageService: LocalStorageService,
    private authService: AuthService,
    private popupNoti: PopupNotiService
  ) {
    // Initialize the form with pre-filled values
    this.signInForm = this.fb.group({
      emailOrUsername: [this.predefinedEmail, Validators.required],
      password: [this.predefinedPassword, Validators.required],
      rememberMe: [false],
    });
  }

  ngOnInit(): void {
    // this.popupNoti.success("Thêm món thành công <a href=\"https://example.com\" target=\"_blank\">tại đây</a>", "Thông báo");
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  loginWithGoogleOnClick() {
    let callbackUrl = "http://localhost:4200/authenticate";
    let authUrl = "https://accounts.google.com/o/oauth2/auth";
    let googleClientId = "704224005006-99quqcrndf1jj5jl4rl8umc0ebrg5l9d.apps.googleusercontent.com";

    let targetUrl = `${authUrl}?redirect_uri=${encodeURIComponent(
      callbackUrl
    )}&response_type=code&client_id=${googleClientId}&scope=openid%20email%20profile`;

    window.location.href = targetUrl;
  }

  onSubmit() {
    const emailOrUsername = this.signInForm.get('emailOrUsername')?.value;
    const password = this.signInForm.get('password')?.value;

    if (this.signInForm.valid) {
      this.authenticateService.login(emailOrUsername, password).subscribe((response: any) => {
        console.log(response);
        if(response.result.authenticated === true) {
          this.authenticated = true;
          this.localStorageService.setToken(response.result.token);
          this.authService.getUserId(this.localStorageService.getToken() || '');
          this.role = this.authService.getRoles()
          if (
            this.authenticated
          ) {
            // Successful login
            this.alert.isVisible = true;
            this.alert.type =
              'relative py-3 text-sm rounded-md ltr:pl-5 rtl:pr-5 ltr:pr-7 rtl:pl-7 bg-green-100 text-green-500';
            this.alert.message = 'Login successful! Redirecting...';

            setTimeout(() => {
              if(this.role.includes('ROLE_USER')){
                this.router.navigate(['/dashboard/apps-ecommerce-restaurant-grid']);
              }
              if(this.role.includes('ROLE_RESTAURANT')){
                this.router.navigate(['/dashboard']);
              }
            }, 1000);
          } else {
            // Show an error alert
            this.alert.isVisible = true;
            this.alert.type =
              'relative py-3 text-sm rounded-md ltr:pl-5 rtl:pr-5 ltr:pr-7 rtl:pl-7 bg-red-100 text-red-500';
            this.alert.message = 'Thông tin đăng nhập chưa chính xác. Vui lòng thử lại!';
          }
        }
      })
    } else {
      // Show an error alert if form is not valid
      this.alert.isVisible = true;
      this.alert.type =
        'relative py-3 text-sm rounded-md ltr:pl-5 rtl:pr-5 ltr:pr-7 rtl:pl-7 bg-red-100 text-red-500';
      this.alert.message = 'Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!';
    }
  }
}
