import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../service/auth-service/auth.service";

export const authGuard: CanActivateFn = (route, state) => {

  const router: Router = inject(Router);
  const authService: AuthService = inject(AuthService);
  const token: string | null = localStorage.getItem('token');

  if(!token){
    //Nếu token ko tồn tại thì chuyển hướng đến trang unauthorized
    router.navigate(['/unauthorized']);
    return false;
  }

  try {
    //Lấy danh sách role trong token
    const roles = authService.getRoles();

    //Lấy role cần thiết từ điều hướng
    const requiredRoles = route.data?.['roles'] as string[];

    //Kiểm tra danh sách role trong token có role cần thiết không
    const hasAccess = requiredRoles.some(role => roles.includes(role));

    //Nếu không có role cần thiết thì chuyển hướng đến trang unauthorized
    if(!hasAccess){
      console.log('Access denied');
      router.navigate(['/unauthorized']);
      return false;
    }

    return true;
  } catch (error) {
    console.error('Invalid token: ', error);
    router.navigate(['/unauthorized']);
    return false;
  }
};
