import { Injectable } from '@angular/core';
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class PopupNotiService {

  constructor(private service: ToastrService) { }

  success(message: string, title?: string) {
    this.service.success(message, title, {
      positionClass: 'toast-top-right',
      timeOut: 3000,
      closeButton: true,
      progressBar: true,
      enableHtml: true
    });
  }

  error(message: string, title?: string) {
    this.service.error(message, title, {
      positionClass: 'toast-top-right',
      timeOut: 3000,
      closeButton: true,
      progressBar: true,
      enableHtml: true
    });
  }

  info(message: string, title?: string) {
    this.service.info(message, title, {
      positionClass: 'toast-top-right',
      timeOut: 3000,
      closeButton: true,
      progressBar: true,
      enableHtml: true
    });
  }

  warning(message: string, title?: string) {
    this.service.warning(message, title, {
      positionClass: 'toast-top-right',
      timeOut: 3000,
      closeButton: true,
      progressBar: true,
      enableHtml: true
    });
  }


}
