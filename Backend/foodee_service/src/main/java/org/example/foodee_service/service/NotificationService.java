package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.notification_request.NotificationCreateRequest;
import org.springframework.stereotype.Service;

public interface NotificationService {
    void sendNotification(String message);

    void createNewOrderNotification(Long orderId);
}
