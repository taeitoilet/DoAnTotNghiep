package org.example.foodee_service.dto.request.order_request;

import lombok.Builder;
import lombok.Data;
import org.example.foodee_service.dto.request.order_item_request.OrderItemCreateRequest;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderCreateRequest {
    private String orderPaymentMethod;

    private String orderPaymentStatus;

    private String orderDeliveryAddress;

    private Long restaurantId;

    private String userId;

    private String userPhone;
    private String userName;

    private BigDecimal orderTotalAmount;

    private List<OrderItemCreateRequest> orderItems;
}
