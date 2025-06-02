package org.example.foodee_service.dto.request.order_item_request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemCreateRequest {

    private Long dishId;

    private BigDecimal orderItemPrice;

    private Integer orderItemQuantity;

    private String orderItemNote;
}
