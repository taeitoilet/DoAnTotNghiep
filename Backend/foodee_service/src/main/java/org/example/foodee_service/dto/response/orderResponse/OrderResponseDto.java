package org.example.foodee_service.dto.response.orderResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import org.example.foodee_service.dto.response.orderItemResponse.OrderItemResponseDto;
import org.example.foodee_service.entity.OrderItem;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDto {
    private Long orderId;

    private BigDecimal orderTotalAmount;

    private String orderStatus;

    private String orderPaymentMethod;

    private String orderPaymentStatus;

    private String orderDeliveryAddress;

    private LocalDateTime orderCreatedAt;

    private String orderCreatedBy;

    private LocalDateTime orderUpdatedAt;

    private String orderUpdatedBy;

    private List<OrderItemResponseDto> orderItems;

    private String userId;

    private Long restaurantId;

    private String userPhone;
    private String userName;
}
