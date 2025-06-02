package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.order_item_request.UpdateOrderItemRequest;
import org.example.foodee_service.entity.OrderItem;

public interface OrderItemService {
    OrderItem updateOrderItem(UpdateOrderItemRequest request);
    void deleteOrderItem(Long id);
}
