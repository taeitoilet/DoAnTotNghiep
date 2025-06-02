package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.order_request.DeleteOrderRequest;
import org.example.foodee_service.dto.request.order_request.SendOrderRequest;
import org.example.foodee_service.dto.request.order_request.UpdateOrderRequest;
import org.example.foodee_service.dto.response.orderResponse.OrderResponseDto;
import org.example.foodee_service.entity.Orders;

public interface OrderService {
    OrderResponseDto updateOrder(UpdateOrderRequest request);
    Orders sendOrder(SendOrderRequest request);
    void deleteOrder(DeleteOrderRequest request);
}
