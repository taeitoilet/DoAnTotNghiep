import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {LucideAngularModule} from "lucide-angular";
import {NgIf} from "@angular/common";
import {ModalService} from "../../../../Core/service/modal/modal.service";
import {ImageUplodingService} from "../../../../service/image-uploding.service";
import {
  emailPattern,
  generateRandomId,
  loginUserImage
} from "../../../../Core/shared functions/shared-functions-varibles";
import {NgSelectComponent} from "@ng-select/ng-select";
import {RoleService} from "../../../../Core/service/role-service/role.service";
import {tap} from "rxjs/operators";
import {switchMap} from "rxjs";
import {UserService} from "../../../../Core/service/user-api-service/user.service";
import {HttpClient} from "@angular/common/http";
import {PopupNotiService} from "../../../../Core/service/popup-noti-service/popup-noti.service";

@Component({
  selector: 'app-add-edit-user',
  imports: [
    FormsModule,
    LucideAngularModule,
    NgIf,
    ReactiveFormsModule,
    NgSelectComponent
  ],
  templateUrl: './add-edit-user.component.html',
  styleUrl: './add-edit-user.component.scss'
})
export class AddEditUserComponent implements OnInit {
  @Input() config: any;
  reviewForm !: FormGroup;
  rating = 0;
  hovering = 0;
  categories: any[] = [];
  statusList: any[] = [
    {
      label: 'Active',
      value: 'ACTIVE'
    },
    {
      label: 'Inactive',
      value: 'INACTIVE'
    }
  ];
  roleList: any[] = [];
  userId: string = '';
  toEditUser: any;

  constructor(public fb: FormBuilder,
              public modalService: ModalService,
              public imgUploading: ImageUplodingService,
              private roleService: RoleService,
              private userService: UserService,
              private http: HttpClient,
              private popup: PopupNotiService
  ) {
    this.reviewForm = this.fb.group({
      addOrEdit: ['add', Validators.required],
      username: ['', Validators.required],
      userFullName: ['', Validators.required],
      userEmail: ['', [Validators.required, Validators.pattern(emailPattern)]],
      userPhone: ['', [Validators.required, Validators.pattern(/^\+?\(?\d{1,3}\)?[\s-]?\d{2,4}[\s-]?\d{2,4}[\s-]?\d{2,4}$/)]],
      gender: ['', Validators.required],
      userStatus: ['Active', Validators.required],
      userId: ['', this.config ? Validators.required : null],
      roles: [[], this.config ? Validators.required : null],
      userAvatarUrl: ['', Validators.required],
    })
  }



  ngOnInit() {
    this.loadRoles();
  }

  loadRoles(){
    if(this.config){
      this.reviewForm.get('addOrEdit')?.setValue('edit');
      this.roleService.getRoles().pipe(
        tap((data: any) => {
          this.roleList = data.data.items
        }),
        switchMap(() => {
          this.userId = this.config.userId ? this.config.userId : '';
          return this.userService.getUserDetail(this.userId)
        })
      )
        .subscribe((data: any) => {
          if(data?.data){
            const user = data.data;
            const role: any[] = user.roles.map((role: any) => role);

            this.toEditUser = user;
            this.reviewForm.patchValue({
              username: user.username,
              userFullName: user.userFullName,
              userEmail: user.userEmail,
              userPhone: user.userPhone,
              gender: "1",
              userStatus: user.userStatus,
              roles: role,
              userAvatarUrl: user.userAvatarUrl,
              newAvatarUrl: user.userAvatarUrl,
              userId: user.userId
            })
          }
        })
    }else {
      this.roleService.getRoles().subscribe((data : any) => {
        if (data) {
          this.roleList = data.data.items
        }
      })
    }
  }

  onSubmit(): void {
    if (!this.reviewForm.valid) {
      return
    }
    const formData = this.reviewForm.value;
    if(this.config){
      this.modalService.close(formData);
    } else {
      const req = {
        userName: formData.username,
        userFullName: formData.userFullName,
        userEmail: formData.userEmail,
        userPhone: formData.userPhone,
        gender: formData.gender,
        userStatus: formData.userStatus,
        userAvatarUrl: formData.userAvatarUrl
      }
      this.userService.createUser(req).subscribe({
        next: (data: any) => {
          this.modalService.close();
          this.popup.success('Thêm người dung thanh cong', 'Thông báo');
        },
        error: (error: any) => {
          this.popup.error(error.error.message, error.error.code);
        }
      })
    }
  }

  onFileChange(event: any) {
    console.log("Avatar is changed")
    if (event.target.files && event.target.files[0]) {
      const formData = new FormData();
      const request = {
        userId: this.userId,
        oldAvatarUrl: this.toEditUser ? this.toEditUser.userAvatarUrl : null
      };

      formData.append('request', new Blob([JSON.stringify(request)], { type: 'application/json' }));

      //Đưa file ảnh mới vào form data
      formData.append('file', event.target.files[0]);

      //Call API
      this.http.post('http://localhost:8080/foodee/authorization/auth/user/upload-avatar/v1.0', formData).subscribe((data: any) => {
        if (data) {
          console.log(data)
          this.reviewForm.get('userAvatarUrl')?.setValue(data.data);
          this.popup.success('Cập nhật ảnh đại diện thành công');
        }
      })
    }
  }

  compareRoles(c1: any, c2: any): boolean {
    return c1 && c2 ? c1.roleName === c2.roleName : c1 === c2;
  }


  closeModal() {
    this.modalService.close()
  }

  changeRating() {
    this.rating = this.reviewForm.get('star')?.value;
  }

  setRating(rating: number): void {
    this.rating = rating;
    this.reviewForm.controls['star'].setValue(rating);
  }

}
