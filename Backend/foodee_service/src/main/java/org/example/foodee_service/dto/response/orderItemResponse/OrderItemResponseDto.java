package org.example.foodee_service.dto.response.orderItemResponse;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponseDto {
    private Long orderItemId;

    private Long orderId;

    private Long dishId;

    private String dishName;

    private Integer orderItemQuantity;

    private BigDecimal orderItemPrice;

    private String orderItemNote;

    private String dishPhotoUrl;
}
