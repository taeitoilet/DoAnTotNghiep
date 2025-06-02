import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { LucideAngularModule } from 'lucide-angular';
import {User} from "../../../Data/models";
import {SearchService} from "../../../service/search.service";
import {TranslateService} from "@ngx-translate/core";
import {HttpClient} from "@angular/common/http";
import {LayoutSettingService} from "../../../layouts/layout-setting.service";
import {AuthenticateService} from "../../../Core/service/authenticate-service/authenticate.service";
import {LocalStorageService} from "../../../service/local-storage.service";
import {UserService} from "../../../Core/service/user-api-service/user.service";
import {DatePipe, NgIf} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ModalService} from "../../../Core/service/modal/modal.service";
import {ImageUplodingService} from "../../../service/image-uploding.service";
import {RoleService} from "../../../Core/service/role-service/role.service";
import {PopupNotiService} from "../../../Core/service/popup-noti-service/popup-noti.service";
import {emailPattern} from "../../../Core/shared functions/shared-functions-varibles";


@Component({
    selector: 'app-pages-account-settings',
  imports: [LucideAngularModule, RouterLink, DatePipe, ReactiveFormsModule, NgIf],
    templateUrl: './pages-account-settings.component.html',
    styleUrl: './pages-account-settings.component.scss'
})
export class PagesAccountSettingsComponent  {
  constructor(public fb: FormBuilder,
              public modalService: ModalService,
              private userService: UserService,private http: HttpClient,private popup: PopupNotiService
  ) {}
  user: User | null = null;
  userForm !: FormGroup;

  ngOnInit(): void {
    this.userService.getMyInfo().subscribe((data: any) => {
      if (data) {
        this.user = data.data;
        this.userForm = this.fb.group({
          fullName: [this.user?.userFullName, Validators.required],
          email: [this.user?.userEmail, [Validators.required, Validators.pattern(emailPattern)]],
          phone: [this.user?.userPhone, [Validators.required, Validators.pattern(/^\+?\(?\d{1,3}\)?[\s-]?\d{2,4}[\s-]?\d{2,4}[\s-]?\d{2,4}$/)]],
        })
      }else{
        this.userForm = this.fb.group({
          fullName: ['', Validators.required],
          email: ['', [Validators.required, Validators.pattern(emailPattern)]],
          phone: ['', [Validators.required, Validators.pattern(/^\+?\(?\d{1,3}\)?[\s-]?\d{2,4}[\s-]?\d{2,4}[\s-]?\d{2,4}$/)]],
        })
      }
    })

  }
  previewUrl: string | ArrayBuffer | null = null;
  selectedFile: File | null = null;
  gender : string = 'Nam'
  onFileSelected(event: Event): void {
    const fileInput = event.target as HTMLInputElement;

    if (fileInput.files && fileInput.files.length > 0) {
      this.selectedFile = fileInput.files[0];

      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
      const formData = new FormData();
      const request = {
        userId: this.user?.userId,
        oldAvatarUrl: this.user?.userAvatarUrl || null
      };

      formData.append('request', new Blob([JSON.stringify(request)], { type: 'application/json' }));
      formData.append('file', this.selectedFile);

      this.http.post('http://localhost:8080/foodee/authorization/auth/user/upload-avatar/v1.0', formData)
        .subscribe({
          next: (data: any) => {
            console.log(data);
            this.popup.success('Cập nhật ảnh đại diện thành công');
            this.userService.getMyInfo().subscribe((data: any) => {
              if (data) {
                this.user = data.data;
              }
            })
          },
          error: (err) => {
            console.error(err);
            this.popup.error('Cập nhật ảnh đại diện thất bại');
          }
        });
    }
  }
  onUpdate(){
    if(this.userForm.valid){
      const req = {
        userId: this.user?.userId,
        username: this.user?.username,
        userFullName: this.userForm.value.fullName,
        userEmail: this.userForm.value.email,
        userPhone:  this.userForm.value.phone,
        gender : this.gender,
      };
      console.log("Hello");
      this.userService.updateUser(req).subscribe({
        next: (data: any) => {
          this.popup.success("Cập nhật người dùng thành công");
          this.userService.getMyInfo().subscribe((data: any) => {
            if (data) {
              this.user = data.data;
            }
          })
          error: (err: any) => {
            console.log(err);
            this.popup.error("Cập nhật người dùng không thành công");
          }
        }
      })
    }else{
      this.userForm.markAllAsTouched
    }
  }
}
