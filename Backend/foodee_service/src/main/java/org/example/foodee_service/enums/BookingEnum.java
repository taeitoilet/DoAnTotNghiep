package org.example.foodee_service.enums;

import lombok.Getter;

@Getter
public enum BookingEnum {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String bookingStatus;
    BookingEnum(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
