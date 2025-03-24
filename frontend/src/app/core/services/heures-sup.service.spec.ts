import { TestBed } from '@angular/core/testing';

import { HeuresSupService } from './heures-sup.service';

describe('HeuresSupService', () => {
  let service: HeuresSupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HeuresSupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
