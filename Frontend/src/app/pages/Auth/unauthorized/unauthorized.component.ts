import { Component } from '@angular/core';
import {LucideAngularModule} from "lucide-angular";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-unauthorized',
    imports: [
        LucideAngularModule,
        RouterLink
    ],
  templateUrl: './unauthorized.component.html',
  styleUrl: './unauthorized.component.scss'
})
export class UnauthorizedComponent {
  constructor(protected router: Router) {
  }
}
