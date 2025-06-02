import {Component, OnChanges, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {LocalStorageService} from "../../../service/local-storage.service";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {AuthService} from "../../../service/auth-service/auth.service";

@Component({
  selector: 'app-authenticate',
  imports: [
    MatProgressSpinner,
  ],
  templateUrl: './authenticate.component.html',
  styleUrl: './authenticate.component.scss'
})
export class AuthenticateComponent implements OnInit, OnChanges {
  isLoggedin: boolean = false;
  role : string[] = []
  constructor(private router: Router, private localStorageService: LocalStorageService,private authService: AuthService) {}

  ngOnInit(): void {
    console.log(window.location.href);
    const authCodeRegex = /code=([^&]+)/;
    const isMatch = window.location.href.match(authCodeRegex);

    if (isMatch) {
      const authCode = isMatch[1];

      fetch(`http://localhost:8080/foodee/auth/outbound/authentication?code=${authCode}`, {
        method: 'POST',
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);

          this.localStorageService.setToken(data.result?.token); // Lưu token vào localStorage
          this.isLoggedin = true;
          console.log(this.isLoggedin);
          console.log(this.localStorageService.getToken());
          this.role = this.authService.getRoles()
          if(this.role.includes('ROLE_RESTAURANT')){
            this.router.navigate(['/dashboard']);
          }
          if(this.role.includes('ROLE_USER')){
            this.router.navigate(['/dashboard/apps-ecommerce-restaurant-grid']);
          }
        });
    }
  }

  ngOnChanges(): void {
    if (this.isLoggedin) {
      this.router.navigate(['/dashboard']);
    }
  }
}
