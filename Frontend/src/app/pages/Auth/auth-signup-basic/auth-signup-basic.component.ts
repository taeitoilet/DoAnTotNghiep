import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';

import { LucideAngularModule } from 'lucide-angular';
import {AuthenticateService} from "../../../Core/service/authenticate-service/authenticate.service";
import {UserRegisRequest} from "../../../Core/service/model/UserRegisRequest";
import {PopupNotiService} from "../../../Core/service/popup-noti-service/popup-noti.service";

@Component({
    selector: 'app-auth-signup-basic',
    imports: [ReactiveFormsModule, CommonModule, LucideAngularModule, RouterLink],
    templateUrl: './auth-signup-basic.component.html',
    styleUrl: './auth-signup-basic.component.scss'
})
export class AuthSignupBasicComponent implements OnInit {
  signUpForm: FormGroup;
  showPassword = false;
  showConfirmPassword = false;
  userToRegis: UserRegisRequest = {
    userName: '',
    userFullName: '',
    userEmail: '',
    userPassword: '',
    userPhone: ''
  };

  constructor(private fb: FormBuilder,
              private authenticateService: AuthenticateService,
              private popupNoti: PopupNotiService,
              private router: Router) {
    this.signUpForm = this.fb.group(
      {
        fullName: ['', Validators.required],
        userName: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        phone: ['', [Validators.required, Validators.pattern(/^\+?\(?\d{1,3}\)?[\s-]?\d{2,4}[\s-]?\d{2,4}[\s-]?\d{2,4}$/)]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]],
        terms: [false, Validators.requiredTrue],
      },
      { validators: this.passwordsMatchValidator }
    );
  }

  ngOnInit(): void {}

  // Custom validator for matching passwords
  passwordsMatchValidator: ValidatorFn = (
    control: AbstractControl
  ): { [key: string]: boolean } | null => {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');
    if (
      password &&
      confirmPassword &&
      password.value !== confirmPassword.value
    ) {
      return { mismatch: true };
    }
    return null;
  };

  validateField(fieldName: string): boolean {
    const field = this.signUpForm.get(fieldName);
    return field ? field.invalid && (field.dirty || field.touched) : false;
  }

  getErrorMessage(fieldName: string): string {
    const field = this.signUpForm.get(fieldName);
    if (field?.hasError('required')) {
      return 'Vui lòng nhập đầy đủ!';
    }
    if (field?.hasError('email')) {
      return 'Email không đúng định dạng.';
    }
    if (field?.hasError('pattern')) {
      return 'Số điện thoại không đúng định dạng.';
    }
    if (field?.hasError('minlength')) {
      return 'Mật khâu phải có ít nhất 6 ký tự';
    }
    if (fieldName === 'confirmPassword' && field?.hasError('mismatch')) {
      return 'Mật khẩu không khớp';
    }
    return '';
  }

  onSubmit(): void {
    if (this.signUpForm.valid) {
      // Handle form submission logic here
      this.userToRegis = {
        userName: this.signUpForm.value.userName,
        userFullName: this.signUpForm.value.fullName,
        userEmail: this.signUpForm.value.email,
        userPassword: this.signUpForm.value.password,
        userPhone: this.signUpForm.value.phone
      };
      this.authenticateService.signup(this.userToRegis).subscribe({
        next: (res) => {
          this.popupNoti.success("Đăng ký tài khoản thành công", "Thông báo");
          this.router.navigate(['/auth-signin-basic']);
        },
        error: (err) => {
          console.log(err);
          for (const key in err.error.errors) {
            this.popupNoti.error(err.error.errors[key].errorDesc, "Lỗi");
          }
        }
      });
    } else {
      // Mark all fields as touched to trigger validation
      this.signUpForm.markAllAsTouched();
    }
  }
}
