package org.example.foodee_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "noti-service", url = "http://localhost:8081") // URL của Noti Service
public interface NotiServiceClient {
    @GetMapping("/api/notifications/send/v1.0") // API mà Foodee sẽ gọi
    void sendNotification(@RequestParam("userId") String userId, @RequestParam("notificationId") Long notificationId);

    @GetMapping("/api/notifications/sendUnread/v1.0") // API mà Foodee sẽ gọi
    void sendUnreadNotification(@RequestParam("userId") String userId);
}
