import { TestBed } from '@angular/core/testing';

import { ReportApiServiceService } from './report-api-service.service';

describe('ReportApiServiceService', () => {
  let service: ReportApiServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportApiServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
