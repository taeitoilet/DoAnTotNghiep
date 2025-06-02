import { Injectable } from '@angular/core';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SseService {
  private eventSource: EventSource | undefined;

  constructor() { }

  //Kết nối đến SSE endpoint
  connectToSSE(userId: string): Observable<MessageEvent> {
    this.eventSource = new EventSource(
      'http://localhost:8081/api/notifications/subscribe?userId=' + userId
    );

    // Trả về Observable từ SSE
    return new Observable((observer) => {

      this.eventSource!.addEventListener('ORDER_STATUS_CHANGED', (event) => {
        try {
          // Parse chuỗi JSON thành object
          const data = JSON.parse(event.data);

          // Gửi dữ liệu qua observer
          observer.next(data);
        } catch (error) {
          console.error('Error parsing SSE data:', error);
        }
      });

      this.eventSource!.addEventListener('ORDER_CREATED', (event) => {
        try {
          // Parse chuỗi JSON thành object
          const data = JSON.parse(event.data);
          // Gửi dữ liệu qua observer
          observer.next(data);
        } catch (error) {
          console.error('Error parsing SSE data:', error);
        }
      });

      this.eventSource!.onmessage = (event) => {
        console.log('Received message:', event.data); // Kiểm tra trong console
        observer.next(event);
      };

      this.eventSource!.onerror = (error) => {
        console.error('Error in SSE connection:', error);
        observer.error(error);
      };

      this.eventSource!.onopen = () => {
        console.log('SSE connection opened');
      };

      // Khi kết nối đóng
      this.eventSource!.addEventListener('close', () => {
        console.log('SSE connection closed');
      });
    });
  }

  // Đóng kết nối SSE khi không cần thiết
  disconnect() {
    if (this.eventSource) {
      this.eventSource.close();
      console.log('SSE connection closed by client');
    }
  }
}
