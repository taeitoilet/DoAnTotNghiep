import { TestBed } from '@angular/core/testing';

import { DishApiServiceService } from './dish-api-service.service';

describe('DishApiServiceService', () => {
  let service: DishApiServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DishApiServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
