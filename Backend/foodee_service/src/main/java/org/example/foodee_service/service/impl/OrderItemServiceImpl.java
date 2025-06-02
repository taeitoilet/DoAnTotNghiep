package org.example.foodee_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.order_item_request.UpdateOrderItemRequest;
import org.example.foodee_service.dto.request.order_request.OrderItemQueryRequest;
import org.example.foodee_service.dto.response.orderItemResponse.OrderItemResponseDto;
import org.example.foodee_service.entity.Dish;
import org.example.foodee_service.entity.OrderItem;
import org.example.foodee_service.entity.Orders;
import org.example.foodee_service.mapper.OrderItemMapper;
import org.example.foodee_service.repository.DishRepository;
import org.example.foodee_service.repository.OrderItemRepository;
import org.example.foodee_service.repository.OrderRepository;
import org.example.foodee_service.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private DishRepository dishRepository;

    private final OrderItemMapper orderItemMapper;

    public List<OrderItemResponseDto> getAllOrderItems(OrderItemQueryRequest request, Long[] total) {
        Pageable pageable = PageRequest.of(request.getPage().getPageNum() - 1, request.getPage().getPageSize());

        Page<OrderItem> orders = orderItemRepository.findAllByOrders_OrderId(pageable, Long.valueOf(request.getOrderId()));
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())){
            total[0] = orders.getTotalElements();
        }
        return orders.stream().map(orderItemMapper::toOrderItemResponseDto).toList();
    }

    @Override
    public OrderItem updateOrderItem(UpdateOrderItemRequest request) {
        OrderItem item = orderItemRepository.findByOrderItemId(request.getOrderItemId());
        Dish dish = dishRepository.findByDishId(item.getDish().getDishId());
        item = OrderItem.builder()
                .orderItemQuantity(request.getQuantity())
                .orderItemPrice(dish.getDishPriceValue().multiply(BigDecimal.valueOf(request.getQuantity())))
                .orderItemNote(request.getOrderItemNote())
                .orderItemUpdatedAt(LocalDateTime.now())
                .build();
        return orderItemRepository.save(item);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
