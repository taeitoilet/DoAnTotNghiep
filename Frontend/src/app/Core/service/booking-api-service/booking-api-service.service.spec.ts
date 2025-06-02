import { TestBed } from '@angular/core/testing';

import { BookingApiServiceService } from './booking-api-service.service';

describe('BookingApiServiceService', () => {
  let service: BookingApiServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookingApiServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
