import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppsEcommerceBookingListComponent } from './apps-ecommerce-booking-list.component';

describe('AppsEcommerceOrdersListComponent', () => {
  let component: AppsEcommerceBookingListComponent;
  let fixture: ComponentFixture<AppsEcommerceBookingListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppsEcommerceBookingListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppsEcommerceBookingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
