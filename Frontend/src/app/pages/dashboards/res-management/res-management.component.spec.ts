import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResManagementComponent } from './res-management.component';

describe('ResManagementComponent', () => {
  let component: ResManagementComponent;
  let fixture: ComponentFixture<ResManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
