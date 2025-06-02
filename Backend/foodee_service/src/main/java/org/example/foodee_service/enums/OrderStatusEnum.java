package org.example.foodee_service.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    INIT("INIT"),
    CANCEL("CANCEL"),
    PENDING_RES("PENDING_RES"),
    REJECTED("REJECTED"),
    APPROVED("APPROVED"),
    FOOD_DONE("FOOD_DONE"),
    SHIPPING("SHIPPING"),
    SUCCESS("SUCCESS")
    ;

    private final String orderStatus;

    OrderStatusEnum(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isValid(String status) {
        for (OrderStatusEnum s : OrderStatusEnum.values()) {
            if (s.getOrderStatus().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}
