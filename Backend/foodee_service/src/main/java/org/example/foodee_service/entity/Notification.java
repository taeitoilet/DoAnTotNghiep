package org.example.foodee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String notificationType;

    private String notificationTitle;

    private String notificationMessage;

    private Boolean notificationIsRead;

    @Column(name = "order_id")
    private Long orderId;

    private LocalDateTime notificationCreatedAt;

    private String notificationCreatedBy;

    private LocalDateTime notificationUpdatedAt;

    private String notificationUpdatedBy;

    @ManyToOne
    private User user;

    @PrePersist
    public void prePersist() {
        notificationCreatedAt = LocalDateTime.now();
        notificationCreatedBy = "SYSTEM";
    }

    @PreUpdate
    public void preUpdate() {
        notificationUpdatedAt = LocalDateTime.now();
        notificationUpdatedBy = "SYSTEM";
    }
}
