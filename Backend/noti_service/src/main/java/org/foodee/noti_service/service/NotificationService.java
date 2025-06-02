package org.foodee.noti_service.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.foodee.noti_service.entity.Notification;
import org.foodee.noti_service.enums.NotificationType;
import org.foodee.noti_service.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

    // Lưu trữ các kết nối SSE của mỗi user
    private final Map<String, SseEmitter> userSessions = new ConcurrentHashMap<>();

    // Thêm kết nối SSE của client vào Map theo userId
    public void addSession(String userId, SseEmitter emitter) {
        // Khi kết nối SSE hoàn tất hoặc hết timeout, xóa session của user
        emitter.onCompletion(() -> {
            log.info("SSE connection completed for user: {}", userId);
            removeSession(userId);
        });

        emitter.onTimeout(() -> {
            log.warn("SSE connection timed out for user: {}", userId);
            removeSession(userId);
        });

        userSessions.put(userId, emitter);
        log.info("Session added for user: {}", userId);
    }

    @Transactional
    public void sendUnreadNoti(String userId){
        SseEmitter emitter = userSessions.get(userId);
        List<Notification> unreads = notificationRepository.findByUserId(userId, threeDaysAgo);
        if (emitter != null) {
            if (unreads.size() == 0)
                log.info("No unread notification for user: {}", userId);
            else
                log.info("You have {} notifications", unreads.size());
            unreads.forEach(noti -> {
                try {
                    emitter.send(SseEmitter.event()
                            .name(noti.getNotificationType())
                            .data(noti)
                            .id("message-id-" + System.currentTimeMillis())  // ID cho sự kiện
                            .reconnectTime(3000));  // Thời gian reconnect nếu có sự cố
                    log.info("Message sent to user: {}", userId);
                } catch (Exception e) {
                    userSessions.remove(userId); // Xóa session nếu có lỗi
                }
            });
        } else {
            log.warn("No active session found for user: {}", userId);
        }
    }

    // Gửi thông báo tới user cụ thể
    @Transactional
    public void sendMessageToUser(String userId, Long notificationId) {
        Notification noti = notificationRepository.findById(notificationId).orElse(null);
        SseEmitter emitter = userSessions.get(userId);
        if (emitter != null) {
            try {
                // Gửi sự kiện với tên 'order-status' và dữ liệu là message
                assert noti != null;
                emitter.send(SseEmitter.event()
                        .name(noti.getNotificationType())
                        .data(noti)
                        .id("message-id-" + System.currentTimeMillis())  // ID cho sự kiện
                        .reconnectTime(3000));  // Thời gian reconnect nếu có sự cố
                log.info("Message sent to user: {}", userId);
            } catch (Exception e) {
                userSessions.remove(userId); // Xóa session nếu có lỗi
            }
        } else {
            log.warn("No active session found for user: {}", userId);
        }
    }

    // Gửi thông báo tới danh sách userId
    @Transactional
    public void sendMessageToUsers(List<String> userIds, String message) {
        for (String userId : userIds) {
            SseEmitter emitter = userSessions.get(userId);
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event()
                            .name(NotificationType.PAYMENT_SUCCESS.name())
                            .data(message)
                            .id("message-id-" + System.currentTimeMillis())
                            .reconnectTime(3000));  // Thời gian reconnect nếu có sự cố
                    log.info("Message sent to user: {}", userId);
                } catch (Exception e) {
                    log.error("Error sending message to user: {}", userId, e);
                    userSessions.remove(userId); // Nếu có lỗi, xóa session của user
                }
            } else {
                log.warn("No active session found for user: {}", userId);
            }
        }
    }

    // Xóa kết nối khi client ngắt kết nối
    public void removeSession(String userId) {
        if (userSessions.containsKey(userId)) {
            userSessions.remove(userId);
            log.info("Session removed for user: {}", userId);
        }
    }
}
