import {Component, OnInit} from '@angular/core';
import {DomixDropdownModule} from "../../../module/domix dropdown/domix-dropdown.module";
import {DomixPaginationComponent} from "../../../componate/domix-pagination/domix-pagination.component";
import {LucideAngularModule} from "lucide-angular";
import {CommonModule, NgForOf, NgIf} from "@angular/common";
import {PageTitleComponent} from "../../../layouts/page-title/page-title.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ColDefs, DomixGridTestComponent} from "../../../componate/domix-grid-test/domix-grid-test.component";
import {RestApiService} from "../../../Core/service/rest-api.service";
import {DomixTableService} from "../../../componate/domix-grid-test/service/domix-table.service";
import {ModalService} from "../../../Core/service/modal/modal.service";
import {DeleteModalComponent} from "../../../componate/domix-delet-model/delete-modal/delete-modal.component";
import {
  AddEditReviewCustomerComponent
} from "../../Apps/Ecommerce/Customers/apps-ecommerce-customer-list/model/add-edit-review/add-edit-review.component";
import {
  Customer
} from "../../Apps/Ecommerce/Customers/apps-ecommerce-customer-list/apps-ecommerce-customer-list.component";
import {Page} from "../../../Core/service/model/page";
import {UserService} from "../../../Core/service/user-api-service/user.service";
import {User} from "../../../Data/models";
import {AddEditUserComponent} from "./add-edit-user/add-edit-user.component";
import {PopupNotiService} from "../../../Core/service/popup-noti-service/popup-noti.service";

@Component({
  selector: 'app-staff-management',
    imports: [
        DomixDropdownModule,
        DomixPaginationComponent,
        LucideAngularModule,
        NgForOf,
        NgIf,
        PageTitleComponent,
        ReactiveFormsModule,
        PageTitleComponent,
        CommonModule,
        FormsModule
    ],
  templateUrl: './staff-management.component.html',
  styleUrl: './staff-management.component.scss'
})

export class StaffManagementComponent extends DomixGridTestComponent implements OnInit{
  allProjectList: User[] = [];
  hasHeaderCheckbox = false;
  page: Page = new Page();

  constructor(
    public restApiService: RestApiService,
    public domiex: DomixTableService,
    private modalService: ModalService,
    private userService: UserService,
    private popup: PopupNotiService
  ) {
    super(domiex);
    this.hasHeaderCheckbox = this.columnDefs.some((col) => col.headerCheckbox);
  }

  ngOnInit(): void {
    this.projectData();
  }
  projectData(page: Page = this.page) {
    this.userService.getUserList(this.page).subscribe((data: any) => {
      this.allProjectList = data.data.items;
      this.gridData = data.data.items;
      this.displayedData = [...this.gridData];
      this.totalItems = data.data.total;
      this.updateDisplayedData();
    })
    console.log("Data is loaded");
  }
  override onPageChange(newPage: number) {
    this.page.pageNum = newPage;
    this.currentPage = newPage;
    this.projectData(this.page);
    //this.updateDisplayedData()
  }

  override nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updateDisplayedData();
    }
  }

  override prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updateDisplayedData();
    }
  }

  override goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      //this.page.pageNum = page;
      this.currentPage = page;
      //this.projectData();
      // this.updateDisplayedData();
    }
  }

  columnDefs: ColDefs[] = [
    {
      field: 'userId',
      headerName: 'ID',
      sortable: true,
      sortDiraction: 'asc',
      headerCheckbox: true,
    },
    {
      field: 'username',
      headerName: 'Tên đăng nhập',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'userFullName',
      headerName: 'Tên đầy đủ',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'userEmail',
      headerName: 'Email',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'userPhone',
      headerName: 'Số điện thoại',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'gender',
      headerName: 'Giới tính',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'roles',
      headerName: 'Vai trò',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      field: 'userStatus',
      headerName: 'Trạng thái hoạt động',
      sortable: true,
      sortDiraction: 'asc',
    },
    {
      headerName: 'Công cụ',
      sortable: false,
    },
  ];
  showConfirms = false;
  showConfirm = false;
  userToDel: any;

  deleteModal(index: number) {
    this.modalService.open(DeleteModalComponent, {}, (result) => {
      if (result) {
        this.displayedData.splice(index, 1);
      }
    });
  }
  openCreateModal(projectdata: any | null, index: number | null) {
    const data = projectdata;

    this.modalService.open(AddEditUserComponent, data, (result) => {
      this.projectData()
      if (result) {
        if (result) {
          const editedData = result;
          console.log('Form data:', editedData);
          const req = {
            userId: editedData.userId,
            username: editedData.username,
            userFullName: editedData.userFullName,
            userEmail: editedData.userEmail,
            userPhone: editedData.userPhone,
            gender: editedData.gender,
          };

          const reqRole = {
            userId: editedData.userId,
            roleName: editedData.roles
          }

          const reqStatus = {
            userId: editedData.userId,
            userStatus: editedData.userStatus
          }
          if(editedData.addOrEdit == 'edit'){
            this.userService.updateUser(req).subscribe({
              next: (res: any) => {
                this.userService.updateUserStatus(reqStatus).subscribe({
                  next: (res: any) => {
                    this.userService.updateUserRole(reqRole).subscribe({
                      next: (res: any) => {
                        this.popup.success("Cập nhật người dùng thành công");
                        this.projectData();
                      },
                      error: (err: any) => {
                        console.log(err);
                        this.popup.error("Cập nhật phân quyền người dùng không thành công");
                      }
                    })
                  },
                  error: (err: any) => {
                    console.log(err);
                    this.popup.error("Cập nhật trạng thái người dùng không thành công");
                  },
                })
              },
              error: (err: any) => {
                console.log(err);
                this.popup.error("Cập nhật người dùng không thành công");
              },
            });
          } else {
            console.log('Add new data: ', editedData);
          }
        }
      }
    });
  }


  delProds(user: any[]) {
    this.showConfirms = false;
    const userId: string[] = user.map((item: any) => item.userId);
    this.userService.deleteUser({userId}).subscribe({
      next: (res) => {
        this.popup.success("Xóa " + userId.length + " bản ghi thành công!", "Thông báo");
        this.projectData();
      },
      error: (err) => {
        this.popup.error("Xóa " + userId.length + " thất bại!", "Thông báo");
      }
    });
  }

  delProd(user: any) {
    const userId: string[] = [user.userId];
    this.showConfirm = false;
    this.userService.deleteUser({userId}).subscribe({
      next: (res) => {
        this.popup.success("Xóa " + user.username + " bản ghi thành công!", "Thông báo");
        this.projectData();
      },
      error: (err) => {
        this.popup.error("Xóa " + user.username + " thất bại!", "Thông báo");
      }
    });
  }
}
