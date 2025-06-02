package org.example.foodee_service.enums;

import lombok.Getter;

@Getter
public enum DishStatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String dishStatus;

    DishStatusEnum(String dishStatus){
        this.dishStatus = dishStatus;
    }
}
