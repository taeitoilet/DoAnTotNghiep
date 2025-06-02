package org.example.foodee_service.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.request.order_request.OrderCreateRequest;
import org.example.foodee_service.dto.response.orderResponse.OrderResponseDto;
import org.example.foodee_service.entity.Dish;
import org.example.foodee_service.entity.OrderItem;
import org.example.foodee_service.entity.Orders;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.enums.OrderStatusEnum;
import org.example.foodee_service.exception.ResourceNotFoundException;
import org.example.foodee_service.repository.DishRepository;
import org.example.foodee_service.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderItemMapper orderItemMapper;

    public Orders toOrders(OrderCreateRequest request) {
        log.info("Restaurant: {}", request.getRestaurantId());
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", request.getRestaurantId()));
        List<OrderItem> orderItems = new ArrayList<>();

        Orders orders = Orders.builder()
                .orderPaymentMethod(request.getOrderPaymentMethod())
                .orderPaymentStatus(request.getOrderPaymentStatus())
                .orderDeliveryAddress(request.getOrderDeliveryAddress())
                .orderStatus(OrderStatusEnum.INIT.getOrderStatus())
                .restaurant(restaurant)
                .userName(request.getUserName())
                .userPhone(request.getUserPhone())
                .orderTotalAmount(request.getOrderTotalAmount())
                .build();
        request.getOrderItems().forEach(item -> {
            Dish dish = dishRepository.findByDishId(item.getDishId());
            orderItems.add(OrderItem.builder()
                    .orderItemQuantity(item.getOrderItemQuantity())
                    .orderItemPrice(item.getOrderItemPrice())
                    .orderItemNote(item.getOrderItemNote())
                    .dish(dish)
                    .orders(orders)
                    .build());
        });

        orders.setOrderItems(orderItems);
        return orders;
    }

    public OrderResponseDto toOrderResponseDtoWithoutItems(Orders orders) {
        return OrderResponseDto.builder()
                .orderId(orders.getOrderId())
                .orderTotalAmount(orders.getOrderTotalAmount())
                .orderStatus(orders.getOrderStatus())
                .orderPaymentMethod(orders.getOrderPaymentMethod())
                .orderPaymentStatus(orders.getOrderPaymentStatus())
                .orderDeliveryAddress(orders.getOrderDeliveryAddress())
                .restaurantId(orders.getRestaurant().getRestaurantId())
                .orderCreatedAt(orders.getOrderCreatedAt())
                .orderCreatedBy(orders.getOrderCreatedBy())
                .orderUpdatedAt(orders.getOrderUpdatedAt())
                .orderUpdatedBy(orders.getOrderUpdatedBy())
                .userName(orders.getUserName())
                .userPhone(orders.getUserPhone())
                .userId(orders.getUser().getUserId())
                .build();
    }

    public OrderResponseDto toOrderResponseDtoWithItems(Orders orders) {
        return OrderResponseDto.builder()
                .orderId(orders.getOrderId())
                .orderTotalAmount(orders.getOrderTotalAmount())
                .orderStatus(orders.getOrderStatus())
                .orderPaymentMethod(orders.getOrderPaymentMethod())
                .orderPaymentStatus(orders.getOrderPaymentStatus())
                .orderDeliveryAddress(orders.getOrderDeliveryAddress())
                .orderItems(orders.getOrderItems().stream().map(orderItemMapper::toOrderItemResponseDto).toList())
                .restaurantId(orders.getRestaurant().getRestaurantId())
                .orderCreatedAt(orders.getOrderCreatedAt())
                .orderCreatedBy(orders.getOrderCreatedBy())
                .orderUpdatedAt(orders.getOrderUpdatedAt())
                .orderUpdatedBy(orders.getOrderUpdatedBy())
                .userName(orders.getUserName())
                .userPhone(orders.getUserPhone())
                .userId(orders.getUser().getUserId())
                .build();
    }
}
