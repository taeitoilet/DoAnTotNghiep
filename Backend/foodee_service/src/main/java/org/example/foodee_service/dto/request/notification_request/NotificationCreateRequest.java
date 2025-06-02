package org.example.foodee_service.dto.request.notification_request;

import lombok.*;
import org.springframework.stereotype.Repository;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationCreateRequest {
    private String notificationType;

    private String notificationTitle;

    private String notificationMessage;
}
