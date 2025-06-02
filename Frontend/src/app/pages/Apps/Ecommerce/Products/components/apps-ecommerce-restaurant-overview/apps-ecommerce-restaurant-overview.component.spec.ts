import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppsEcommerceRestaurantOverviewComponent } from './apps-ecommerce-restaurant-overview.component';

describe('AppsEcommerceProductOverviewComponent', () => {
  let component: AppsEcommerceRestaurantOverviewComponent;
  let fixture: ComponentFixture<AppsEcommerceRestaurantOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppsEcommerceRestaurantOverviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppsEcommerceRestaurantOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
