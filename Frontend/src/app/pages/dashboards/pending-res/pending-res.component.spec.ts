import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingResComponent } from './pending-res.component';

describe('PendingResComponent', () => {
  let component: PendingResComponent;
  let fixture: ComponentFixture<PendingResComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PendingResComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PendingResComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
