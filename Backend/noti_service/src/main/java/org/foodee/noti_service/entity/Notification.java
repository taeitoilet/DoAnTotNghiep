package org.foodee.noti_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "notification_title")
    private String notificationTitle;

    @Column(name = "notification_message")
    private String notificationMessage;

    @Column(name = "notification_is_read")
    private Boolean notificationIsRead;

    @Column(name = "notification_created_at")
    private LocalDateTime notificationCreatedAt;

    @Column(name = "notification_created_by")
    private String notificationCreatedBy;

    @Column(name = "notification_updated_at")
    private LocalDateTime notificationUpdatedAt;

    @Column(name = "notification_updated_by")
    private String notificationUpdatedBy;

    @Column(name = "user_user_id")
    private String userId;

    @Column(name = "order_id")
    private Long orderId;
}
