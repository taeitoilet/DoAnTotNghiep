import { HttpInterceptorFn } from '@angular/common/http';
import {inject} from "@angular/core";
import {AuthService} from "../service/auth-service/auth.service";
import {Router} from "@angular/router";
import {catchError, switchMap, throwError} from "rxjs";

export interface APIResponseModel{
  message: string;
  result: any;
  code: any;
}

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  const PUBLIC_ENDPOINTS = ["/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh", "/auth/outbound/authentication","images"];

  const isPublicEndpoint = PUBLIC_ENDPOINTS.some(ep => (req.url.includes(ep) && req.method === 'POST') || req.url.includes('/article/search'));

  const authService = inject(AuthService);
  const router = inject(Router);

  if (isPublicEndpoint) {
    return next(req);
  }else if (token) {
    //Clone request và thêm header nếu token tồn tại
    const cloneRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    //Gửi request kèm header đã thêm token
    return next(cloneRequest).pipe(
      catchError(err => {
        if(err.status === 401){
          //Nếu gặp lỗi 401 thì thử refresh token
          const body ={
            token: token
          }

          return authService.refreshToken(body).pipe(
            switchMap((res: any) => {
              if(res.message != "Unauthenticated") {
                //Nếu refresh thành công thì ghi token mới với localStorage
                localStorage.setItem('token', res.result.token);
              }

              //Clone request với token mới
              //req.headers.set('Authorization', `Bearer ${res.result.token}`);

              const newRequest = req.clone({
                setHeaders: {
                  Authorization: `Bearer ${localStorage.getItem('token')}`,
               }
              });

              //Gửi lại request ban đầu với token mới
              return next(newRequest);
            }),
            catchError(err => {
              if(err.status !== 200){
                //Nếu yêu cầu refresh thất bại thì chuyển về trang login
                router.navigateByUrl('auth-signin-basic')
              }
              return throwError(() => err);
            })
          );
        }
        return throwError(() => err);
      }),
    );
  }

  //Gửi request gốc không kèm token
  return next(req);
};
