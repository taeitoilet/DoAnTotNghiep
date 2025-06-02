import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import {HttpBaseService} from "../http-base-service/http-base.service";
import {of} from "rxjs";
import {environment} from "../../../environments/environment.development";

describe('AuthService', () => {
  let service: AuthService;
  let httpBaseServiceSpy: jasmine.SpyObj<HttpBaseService>;

  // Token hợp lệ với 3 phần: header, payload, và signature
  const mockValidToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIn0.mock-signature';
  // Token không hợp lệ nhưng đủ 3 phần
  const mockInvalidToken = 'header.invalid-payload.signature';

  beforeEach(() => {
    const spy = jasmine.createSpyObj('HttpBaseService', ['post']);
    TestBed.configureTestingModule({
      providers: [
        AuthService,
        { provide: HttpBaseService, useValue: spy }
      ]
    });
    service = TestBed.inject(AuthService);
    httpBaseServiceSpy = TestBed.inject(HttpBaseService) as jasmine.SpyObj<HttpBaseService>;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getDecodedToken', () => {
    it('should decode valid token', () => {
      const decodedToken = service.getDecodedToken(mockValidToken);
      expect(decodedToken).toBeTruthy();
      expect(decodedToken.scope).toBe('ROLE_ADMIN ROLE_USER');
    });

    it('should handle invalid token format', () => {
      expect(() => service.getDecodedToken('invalid-format')).not.toThrow();
      expect(service.getDecodedToken('invalid-format')).toBeNull();
    });

    it('should handle null token', () => {
      expect(service.getDecodedToken(null as any)).toBeNull();
    });

    it('should handle undefined token', () => {
      expect(service.getDecodedToken(undefined as any)).toBeNull();
    });
  });

  describe('getRoles', () => {
    it('should return empty array when no token in localStorage', () => {
      spyOn(localStorage, 'getItem').and.returnValue(null);
      const roles = service.getRoles();
      expect(roles).toEqual([]);
    });

    it('should return roles array from valid token', () => {
      spyOn(localStorage, 'getItem').and.returnValue(mockValidToken);
      const roles = service.getRoles();
      expect(roles).toEqual(['ROLE_ADMIN', 'ROLE_USER']);
    });

    it('should return empty array for invalid token', () => {
      spyOn(localStorage, 'getItem').and.returnValue(mockInvalidToken);
      const roles = service.getRoles();
      expect(roles).toEqual([]);
    });

    it('should handle null token in localStorage', () => {
      spyOn(localStorage, 'getItem').and.returnValue(null);
      const roles = service.getRoles();
      expect(roles).toEqual([]);
    });
  });

  describe('hasRole', () => {
    it('should return true when user has role', () => {
      spyOn(service, 'getRoles').and.returnValue(['ROLE_ADMIN', 'ROLE_USER']);
      expect(service.hasRole('ROLE_ADMIN')).toBeTrue();
    });

    it('should return false when user does not have role', () => {
      spyOn(service, 'getRoles').and.returnValue(['ROLE_ADMIN', 'ROLE_USER']);
      expect(service.hasRole('ROLE_MANAGER')).toBeFalse();
    });

    it('should return false when roles array is empty', () => {
      spyOn(service, 'getRoles').and.returnValue([]);
      expect(service.hasRole('ROLE_ADMIN')).toBeFalse();
    });

    it('should handle undefined role parameter', () => {
      spyOn(service, 'getRoles').and.returnValue(['ROLE_ADMIN']);
      expect(service.hasRole(undefined as any)).toBeFalse();
    });
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getDecodedToken', () => {
    it('should decode valid token', () => {
      const decodedToken = service.getDecodedToken(mockValidToken);
      expect(decodedToken).toBeTruthy();
      expect(decodedToken.scope).toBe('ROLE_ADMIN ROLE_USER');
    });

    it('should return null for invalid token', () => {
      const decodedToken = service.getDecodedToken(mockInvalidToken);
      expect(decodedToken).toBeNull();
    });
  });

  describe('getRoles', () => {
    it('should return empty array when no token in localStorage', () => {
      spyOn(localStorage, 'getItem').and.returnValue(null);
      const roles = service.getRoles();
      expect(roles).toEqual([]);
    });

    it('should return roles array from valid token', () => {
      spyOn(localStorage, 'getItem').and.returnValue(mockValidToken);
      const roles = service.getRoles();
      expect(roles).toEqual(['ROLE_ADMIN', 'ROLE_USER']);
    });

    it('should return empty array for invalid token', () => {
      spyOn(localStorage, 'getItem').and.returnValue(mockInvalidToken);
      const roles = service.getRoles();
      expect(roles).toEqual([]);
    });
  });

  describe('hasRole', () => {
    it('should return true when user has role', () => {
      spyOn(localStorage, 'getItem').and.returnValue(mockValidToken);
      expect(service.hasRole('ROLE_ADMIN')).toBeTrue();
    });

    it('should return false when user does not have role', () => {
      spyOn(localStorage, 'getItem').and.returnValue(mockValidToken);
      expect(service.hasRole('ROLE_MANAGER')).toBeFalse();
    });

    it('should return false when no token exists', () => {
      spyOn(localStorage, 'getItem').and.returnValue(null);
      expect(service.hasRole('ROLE_ADMIN')).toBeFalse();
    });
  });

  describe('login', () => {
    it('should call login API with correct parameters', () => {
      const mockResponse = { token: 'mock-token' };
      httpBaseServiceSpy.post.and.returnValue(of(mockResponse));

      const username = 'testuser';
      const password = 'testpass';

      service.login(username, password).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpBaseServiceSpy.post).toHaveBeenCalledWith(
        environment.httpBaseUrl + 'auth/log-in',
        { username, password }
      );
    });
  });

  describe('refreshToken', () => {
    it('should call refresh token API with correct parameters', () => {
      const mockResponse = { token: 'new-token' };
      httpBaseServiceSpy.post.and.returnValue(of(mockResponse));

      const refreshTokenRequest = { token: 'old-token' };

      service.refreshToken(refreshTokenRequest).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpBaseServiceSpy.post).toHaveBeenCalledWith(
        environment.httpBaseUrl + 'auth/refresh',
        refreshTokenRequest
      );
    });
  });

  describe('logout', () => {
    it('should call logout API with correct parameters', () => {
      const mockResponse = { success: true };
      httpBaseServiceSpy.post.and.returnValue(of(mockResponse));

      const token = { token: 'mock-token' };

      service.logout(token).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpBaseServiceSpy.post).toHaveBeenCalledWith(
        environment.httpBaseUrl + 'auth/logout',
        token
      );
    });
  });

  it('should handle and return empty array when error occurs', () => {
    spyOn(localStorage, 'getItem').and.returnValue(mockValidToken);
    spyOn(service, 'getDecodedToken').and.throwError('Simulated error');

    // Spy on console.error to verify it's called
    spyOn(console, 'error');

    const roles = service.getRoles();

    expect(roles).toEqual([]);
    expect(console.error).toHaveBeenCalledWith(
      'Error getting roles: ',
      jasmine.any(Error)
    );
  });

  it('should handle error when localStorage throws error', () => {
    spyOn(localStorage, 'getItem').and.throwError('localStorage error');
    spyOn(console, 'error');

    const roles = service.getRoles();

    expect(roles).toEqual([]);
    expect(console.error).toHaveBeenCalledWith(
      'Error getting roles: ',
      jasmine.any(Error)
    );
  });
});
