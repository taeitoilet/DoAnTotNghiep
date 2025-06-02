import { TestBed } from '@angular/core/testing';
import { HttpBaseService } from './http-base.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { of } from 'rxjs';

describe('HttpBaseService', () => {
  let service: HttpBaseService;
  let httpClientSpy: jasmine.SpyObj<HttpClient>;

  beforeEach(() => {
    const spy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);
    TestBed.configureTestingModule({
      providers: [
        HttpBaseService,
        { provide: HttpClient, useValue: spy }
      ]
    });
    service = TestBed.inject(HttpBaseService);
    httpClientSpy = TestBed.inject(HttpClient) as jasmine.SpyObj<HttpClient>;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('GET requests', () => {
    it('should make GET request with default headers', () => {
      const mockResponse = { data: 'test' };
      httpClientSpy.get.and.returnValue(of(mockResponse));

      service.get('test-url').subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpClientSpy.get).toHaveBeenCalledWith(
        'test-url',
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });

    it('should make GET request with custom headers and params', () => {
      const mockResponse = { data: 'test' };
      const customHeaders = { 'Authorization': 'Bearer token' };
      const params = { 'page': '1', 'size': '10' };

      httpClientSpy.get.and.returnValue(of(mockResponse));

      service.get('test-url', params, customHeaders).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpClientSpy.get).toHaveBeenCalledWith(
        'test-url',
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });
  });

  describe('POST requests', () => {
    it('should make POST request with body and default headers', () => {
      const mockResponse = { success: true };
      const requestBody = { data: 'test' };

      httpClientSpy.post.and.returnValue(of(mockResponse));

      service.post('test-url', requestBody).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpClientSpy.post).toHaveBeenCalledWith(
        'test-url',
        requestBody,
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });

    it('should make POST request with custom headers and params', () => {
      const mockResponse = { success: true };
      const requestBody = { data: 'test' };
      const customHeaders = { 'Authorization': 'Bearer token' };
      const params = { 'type': 'test' };

      httpClientSpy.post.and.returnValue(of(mockResponse));

      service.post('test-url', requestBody, params, customHeaders).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpClientSpy.post).toHaveBeenCalledWith(
        'test-url',
        requestBody,
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });
  });

  describe('PUT requests', () => {
    it('should make PUT request with body and default headers', () => {
      const mockResponse = { success: true };
      const requestBody = { data: 'test' };

      httpClientSpy.put.and.returnValue(of(mockResponse));

      service.put('test-url', requestBody).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpClientSpy.put).toHaveBeenCalledWith(
        'test-url',
        requestBody,
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });

    it('should make PUT request with custom headers and params', () => {
      const mockResponse = { success: true };
      const requestBody = { data: 'test' };
      const customHeaders = { 'Authorization': 'Bearer token' };
      const params = { 'type': 'test' };

      httpClientSpy.put.and.returnValue(of(mockResponse));

      service.put('test-url', requestBody, params, customHeaders).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpClientSpy.put).toHaveBeenCalledWith(
        'test-url',
        requestBody,
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });
  });

  describe('DELETE requests', () => {
    it('should make DELETE request with default headers', () => {
      const mockResponse = { success: true };

      httpClientSpy.delete.and.returnValue(of(mockResponse));

      service.delete('test-url').subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpClientSpy.delete).toHaveBeenCalledWith(
        'test-url',
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });

    it('should make DELETE request with custom headers and params', () => {
      const mockResponse = { success: true };
      const customHeaders = { 'Authorization': 'Bearer token' };
      const params = { 'id': '123' };

      httpClientSpy.delete.and.returnValue(of(mockResponse));

      service.delete('test-url', params, customHeaders).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });

      expect(httpClientSpy.delete).toHaveBeenCalledWith(
        'test-url',
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });
  });

  describe('Header and Params handling', () => {
    it('should handle undefined headers and params', () => {
      const mockResponse = { data: 'test' };
      httpClientSpy.get.and.returnValue(of(mockResponse));

      service.get('test-url', undefined, undefined).subscribe();

      expect(httpClientSpy.get).toHaveBeenCalledWith(
        'test-url',
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });

    it('should handle null headers and params', () => {
      const mockResponse = { data: 'test' };
      httpClientSpy.get.and.returnValue(of(mockResponse));

      service.get('test-url', null as any, null as any).subscribe();

      expect(httpClientSpy.get).toHaveBeenCalledWith(
        'test-url',
        jasmine.objectContaining({
          headers: jasmine.any(HttpHeaders),
          params: jasmine.any(HttpParams)
        })
      );
    });
  });
});
