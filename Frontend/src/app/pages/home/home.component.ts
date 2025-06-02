import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  featuredRestaurants = [
    {
      id: 1,
      name: 'Nhà hàng Phở Việt',
      image: 'assets/images/restaurants/pho-viet.jpg',
      rating: 4.5,
      cuisine: 'Việt Nam'
    },
    {
      id: 2,
      name: 'Sushi Express',
      image: 'assets/images/restaurants/sushi.jpg',
      rating: 4.8,
      cuisine: 'Nhật Bản'
    },
    {
      id: 3,
      name: 'Pizza House',
      image: 'assets/images/restaurants/pizza.jpg',
      rating: 4.3,
      cuisine: 'Ý'
    }
  ];

  promotions = [
    {
      id: 1,
      title: 'Giảm 50% cho đơn hàng đầu tiên',
      image: 'assets/images/promotions/new-user.jpg',
      code: 'NEWUSER50'
    },
    {
      id: 2,
      title: 'Freeship cho đơn từ 100K',
      image: 'assets/images/promotions/freeship.jpg',
      code: 'FREESHIP100'
    }
  ];

  categories = [
    { id: 1, name: 'Món Việt', icon: 'vietnam' },
    { id: 2, name: 'Món Nhật', icon: 'japan' },
    { id: 3, name: 'Món Hàn', icon: 'korea' },
    { id: 4, name: 'Đồ uống', icon: 'drinks' },
    { id: 5, name: 'Đồ ăn nhanh', icon: 'fast-food' },
    { id: 6, name: 'Món chay', icon: 'vegetarian' }
  ];

  constructor() { }

  ngOnInit(): void {
  }
} 