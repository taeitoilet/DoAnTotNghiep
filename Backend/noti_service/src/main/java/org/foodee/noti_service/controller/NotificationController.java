package org.foodee.noti_service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.foodee.noti_service.entity.Notification;
import org.foodee.noti_service.service.EmailService;
import org.foodee.noti_service.service.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class NotificationController {
    private final EmailService emailService;
    private final NotificationService notificationService;

//    // Endpoint SSE cho các sự kiện thông báo (được thực hiện bằng Flux)
//    @CrossOrigin(origins = "http://localhost:63343")
//    @GetMapping(value = "/api/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> streamNotifications() {
//        emailService.sendSimpleEmail("nguyenminhcoang.work@gmail.com", "New order", "Test new order email to nguyenminhcoang.work@gmail.com");
//        // Giả lập gửi thông báo mỗi 5 giây
//        return Flux.interval(Duration.ofSeconds(5))
//                .map(sequence -> "New notification: Order " + sequence);
//    }


    // Endpoint để client kết nối SSE
//    @CrossOrigin(origins = "http://localhost:63342")
//    @GetMapping(value = "/api/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter streamNotifications(@RequestParam String userId) {
//        log.info("New connection: " + userId);
//        // Tạo SseEmitter để quản lý kết nối của client
//        SseEmitter emitter = new SseEmitter();
//        // Lưu kết nối của client vào service
//        notificationService.addSession(userId, emitter);
//
//        notificationService.sendMessageToUser("123", "Hello");
//        log.info("Message sent: " + userId);
//
//        // Khi client ngắt kết nối, xóa kết nối khỏi Map
//        emitter.onCompletion(() -> {
//            log.info("SSE connection completed for user: {}", userId);
//            notificationService.removeSession(userId);
//        });
//        emitter.onTimeout(() -> {
//            log.info("SSE connection timed out for user: {}", userId);
//            notificationService.removeSession(userId);
//        });
//
//        // Trả về emitter để giữ kết nối SSE
//        return emitter;
//    }

    // API nhận yêu cầu và gửi thông báo cho danh sách các user
    @CrossOrigin(origins = "*")
    @GetMapping("/api/notifications/send/v1.0")
    public ResponseEntity<Map<String, String>> sendNotification(
            @RequestParam String userId,
            @RequestParam Long notificationId) {

        // Gửi thông báo tới userId qua SSE
        notificationService.sendMessageToUser(userId, notificationId);

        // Trả về phản hồi JSON hợp lệ
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Notification sent successfully.");

        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/api/notifications/sendUnread/v1.0")
    public ResponseEntity<Map<String, String>> sendUnreadNotification(
            @RequestParam String userId) {
        // Gửi thông báo tới userId qua SSE
        notificationService.sendUnreadNoti(userId);

        // Trả về phản hồi JSON hợp lệ
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Notification sent successfully.");

        return ResponseEntity.ok(response);
    }

    // API để client kết nối SSE
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
    @GetMapping(value = "/api/notifications/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToNotifications(@RequestParam String userId) {
        SseEmitter emitter = new SseEmitter(0L);
        notificationService.addSession(userId, emitter);
        notificationService.sendUnreadNoti(userId);
        emitter.onCompletion(() -> notificationService.removeSession(userId));
        emitter.onTimeout(() -> notificationService.removeSession(userId));
        // Trả về emitter để giữ kết nối mở
        return emitter;
    }

}
