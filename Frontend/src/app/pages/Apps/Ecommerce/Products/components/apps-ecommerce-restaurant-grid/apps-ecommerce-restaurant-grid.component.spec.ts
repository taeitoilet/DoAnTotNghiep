import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppsEcommerceRestaurantGridComponent } from './apps-ecommerce-restaurant-grid.component';

describe('AppsEcommerceProductsGridComponent', () => {
  let component: AppsEcommerceRestaurantGridComponent;
  let fixture: ComponentFixture<AppsEcommerceRestaurantGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppsEcommerceRestaurantGridComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppsEcommerceRestaurantGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
