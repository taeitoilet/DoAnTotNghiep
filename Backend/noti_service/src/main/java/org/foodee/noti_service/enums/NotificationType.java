package org.foodee.noti_service.enums;

public enum NotificationType {
    ORDER_CREATED("ORDER_CREATED"),
    ORDER_STATUS_CHANGED("ORDER_STATUS_CHANGED"),
    PAYMENT_SUCCESS("PAYMENT_SUCCESS"),
    DELIVERY_UPDATED("DELIVERY_UPDATED");

    private final String NotificationType;

    NotificationType(String NotificationType) {
        this.NotificationType = NotificationType;
    }
}
