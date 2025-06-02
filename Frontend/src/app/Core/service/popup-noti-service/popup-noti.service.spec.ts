import { TestBed } from '@angular/core/testing';

import { PopupNotiService } from './popup-noti.service';

describe('PopupNotiService', () => {
  let service: PopupNotiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PopupNotiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
