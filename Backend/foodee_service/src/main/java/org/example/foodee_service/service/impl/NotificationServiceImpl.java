package org.example.foodee_service.service.impl;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.client.NotiServiceClient;
import org.example.foodee_service.dto.request.notification_request.MarkAsReadRequest;
import org.example.foodee_service.dto.request.notification_request.NotificationCreateRequest;
import org.example.foodee_service.entity.Notification;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.enums.NotificationType;
import org.example.foodee_service.repository.NotificationRepository;
import org.example.foodee_service.repository.RestaurantRepository;
import org.example.foodee_service.repository.UserRepository;
import org.example.foodee_service.service.NotificationService;
import org.example.foodee_service.util.SecurityUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationServiceImpl {

    private final NotificationRepository notificationRepository;
    private final RestaurantRepository restaurantRepository;
    private final SecurityUtil securityUtil;
    private final NotiServiceClient notiServiceClient;
    private final UserRepository userRepository;


    public void sendNotification(String recieverId, Long notificationId) {
        notiServiceClient.sendNotification(recieverId, notificationId);
    }

    @Transactional
    public void markNotificationAsRead(MarkAsReadRequest request) {
        notificationRepository.markAsRead(request.getNotificationIds());
//        notiServiceClient.sendUnreadNotification(securityUtil.getCurrentUser().getUserId());
    }

    @Async
    public void createNewOrderNotification(Long orderId, String userId) {
        User u = userRepository.findUserByUserId(userId);
        Notification noti = Notification.builder()
                .orderId(orderId)
                .notificationIsRead(false)
                .notificationMessage("Bạn vừa có một đơn hàng mới")
                .notificationType(NotificationType.ORDER_CREATED.name())
                .user(u)
                .notificationTitle("Đơn hàng mới")
                .build();

        log.info("Notification created {}", noti.getNotificationId());
        Notification n = notificationRepository.save(noti);
        log.info("Notification created {}", n.getNotificationId());
        sendNotification(userId, n.getNotificationId());
    }

    @Async
    public void createNewBookingNotification(Long orderId, String userId) {
        User u = userRepository.findUserByUserId(userId);
        Notification noti = Notification.builder()
                .orderId(orderId)
                .notificationIsRead(false)
                .notificationMessage("Bạn vừa có một khách đặt bàn")
                .notificationType(NotificationType.ORDER_CREATED.name())
                .user(u)
                .notificationTitle("Đơn đặt bàn mới")
                .build();
        Notification n = notificationRepository.save(noti);
        sendNotification(userId, n.getNotificationId());
    }


    @Async
    public void approvedOrderStatusNotification(Long orderId, String userId) {
        Notification noti = Notification.builder()
                .orderId(orderId)
                .notificationIsRead(false)
                .notificationTitle("Đơn hàng sẽ sớm được giao tới bạn")
                .notificationType(NotificationType.ORDER_STATUS_CHANGED.name())
                .user(securityUtil.getCurrentUser())
                .notificationMessage("Đơn hàng của bạn đã được chấp nhận")
                .build();

        Notification n = notificationRepository.save(noti);
        sendNotification(userId, n.getNotificationId());
    }
    public void approvedBookingStatusNotification(Long orderId, String userId) {
        Notification noti = Notification.builder()
                .orderId(orderId)
                .notificationIsRead(false)
                .notificationTitle("Đặt bàn thành công")
                .notificationType(NotificationType.ORDER_STATUS_CHANGED.name())
                .user(securityUtil.getCurrentUser())
                .notificationMessage("Đơn đặt bàn của bạn đã được chấp nhận")
                .build();

        Notification n = notificationRepository.save(noti);
        sendNotification(userId, n.getNotificationId());
    }
    public void rejectOrderStatusNotification(Long orderId, String userId) {
        Notification noti = Notification.builder()
                .orderId(orderId)
                .notificationIsRead(false)
                .notificationType("Đơn hàng của bạn đã bị từ chối. Vui lòng đặt món khác")
                .notificationType(NotificationType.ORDER_STATUS_CHANGED.name())
                .user(securityUtil.getCurrentUser())
                .notificationMessage("Đơn hàng của bạn đã bị từ chối")
                .build();

        Notification n = notificationRepository.save(noti);
        sendNotification(userId, n.getNotificationId());
    }
    public void rejectBookingStatusNotification(Long orderId, String userId) {
        Notification noti = Notification.builder()
                .orderId(orderId)
                .notificationIsRead(false)
                .notificationTitle("Đặt bàn thất bại")
                .notificationType(NotificationType.ORDER_STATUS_CHANGED.name())
                .user(securityUtil.getCurrentUser())
                .notificationMessage("Đơn đặt bàn của bạn đã bị nhà hàng từ chối!")
                .build();

        Notification n = notificationRepository.save(noti);
        sendNotification(userId, n.getNotificationId());
    }

    @Async
    public void receiveOrderNotification(Long orderId, Long restaurantId) {
        Notification noti = Notification.builder()
                .orderId(orderId)
                .notificationIsRead(false)
                .notificationMessage("Your restaurant has received an new order")
                .notificationType(NotificationType.ORDER_STATUS_CHANGED.name())
                .user(securityUtil.getCurrentUser())
                .notificationTitle("Receive order")
                .build();

        Restaurant res = restaurantRepository.getByRestaurantId(restaurantId);

        Notification n = notificationRepository.save(noti);
        sendNotification(res.getOwner().getUserId(), n.getNotificationId());
    }
}
