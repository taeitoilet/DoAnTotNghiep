package org.example.foodee_service.mapper;

import org.example.foodee_service.dto.response.orderItemResponse.OrderItemResponseDto;
import org.example.foodee_service.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .orderId(orderItem.getOrders().getOrderId())
                .dishId(orderItem.getDish() != null ? orderItem.getDish().getDishId() : null)
                .dishName(orderItem.getDish() != null ? orderItem.getDish().getDishName() : null)
                .orderItemQuantity(orderItem.getOrderItemQuantity())
                .orderItemPrice(orderItem.getOrderItemPrice())
                .orderItemNote(orderItem.getOrderItemNote())
                .dishPhotoUrl(orderItem.getDish() != null ? orderItem.getDish().getDishImageUrl() : null)
                .build();
    }
}
