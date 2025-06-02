import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

const routes: Routes = [
  { path: '', component: HomeComponent }, // Trang chủ sẽ hiển thị ở URL gốc
  { path: 'home', redirectTo: '', pathMatch: 'full' }, // Redirect /home về trang chủ
  { path: '**', redirectTo: '' } // Redirect tất cả các URL không hợp lệ về trang chủ
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { } 