package org.example.foodee_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.request.order_request.*;
import org.example.foodee_service.dto.response.orderResponse.OrderResponseDto;
import org.example.foodee_service.entity.OrderItem;
import org.example.foodee_service.entity.Orders;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.enums.OrderStatusEnum;
import org.example.foodee_service.exception.AppError;
import org.example.foodee_service.exception.AppException;
import org.example.foodee_service.mapper.OrderMapper;
import org.example.foodee_service.repository.OrderItemRepository;
import org.example.foodee_service.repository.OrderRepository;
import org.example.foodee_service.repository.RestaurantRepository;
import org.example.foodee_service.repository.UserRepository;
import org.example.foodee_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderMapper orderMapper;
    private final NotificationServiceImpl notificationService;


    public OrderResponseDto createOrder(OrderCreateRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByUserId(jwt.getClaimAsString("userId"));
        Restaurant restaurant = restaurantRepository.getByRestaurantId(request.getRestaurantId());
        Orders orders = orderMapper.toOrders(request);
        orders.setOrderStatus(OrderStatusEnum.INIT.getOrderStatus());
        orders.setUser(user);
        Orders newOrder = orderRepository.save(orders);
        if(restaurant.getOwner() != null){
            notificationService.createNewOrderNotification(newOrder.getOrderId(), restaurant.getOwner().getUserId());
        }
        return orderMapper.toOrderResponseDtoWithoutItems(newOrder);
    }

    public List<OrderResponseDto> getAllOrders(OrderQueryRequest request, Long[] total) {
        //Còn phần critieria chưa làm
        Pageable pageable = PageRequest.of(request.getPage().getPageNum() - 1, request.getPage().getPageSize());
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        Page<Orders> orders = orderRepository.findAllOrders(restaurant.getRestaurantId(),pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())){
            total[0] = orders.getTotalElements();
        }

        return orders.stream().map(orderMapper::toOrderResponseDtoWithItems).toList();
    }
    public List<OrderResponseDto> getOrdersByUser(OrderQueryRequest request, Long[] total) {
        //Còn phần critieria chưa làm
        Pageable pageable = PageRequest.of(request.getPage().getPageNum() - 1, request.getPage().getPageSize());
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Orders> orders = orderRepository.findOrdersByUser(jwt.getClaimAsString("userId"),pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())){
            total[0] = orders.getTotalElements();
        }
        return orders.stream().map(orderMapper::toOrderResponseDtoWithItems).toList();
    }
    public List<OrderResponseDto> getAllOrdersByUser(OrderQueryRequest request, Long[] total) {
        //Còn phần critieria chưa làm
        Pageable pageable = PageRequest.of(request.getPage().getPageNum() - 1, request.getPage().getPageSize());
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Orders> orders = orderRepository.findAllOrdersByUser(jwt.getClaimAsString("userId"),pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())){
            total[0] = orders.getTotalElements();
        }
        return orders.stream().map(orderMapper::toOrderResponseDtoWithItems).toList();
    }
    public List<OrderResponseDto> getPendingOrders(OrderQueryRequest request, Long[] total) {
        //Còn phần critieria chưa làm
        Pageable pageable = PageRequest.of(request.getPage().getPageNum() - 1, request.getPage().getPageSize());
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        Page<Orders> orders = orderRepository.findPendingOrders(restaurant.getRestaurantId(),pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())){
            total[0] = orders.getTotalElements();
        }

        return orders.stream().map(orderMapper::toOrderResponseDtoWithItems).toList();
    }


    public OrderResponseDto getOrderbyId(Long orderId) {
        return orderMapper.toOrderResponseDtoWithItems(orderRepository.findByOrderId(orderId));
    }


    @Override
    public OrderResponseDto updateOrder(UpdateOrderRequest request) {
        Orders orders = orderRepository.findByOrderId(request.getOrderId());
        orders.setOrderStatus(request.getOrderStatus().getOrderStatus());
        if (request.getOrderStatus().getOrderStatus().equals(OrderStatusEnum.APPROVED.getOrderStatus())) {
            notificationService.approvedOrderStatusNotification(orders.getOrderId(), orders.getUser().getUserId());
        }
        if (request.getOrderStatus().getOrderStatus().equals(OrderStatusEnum.REJECTED.getOrderStatus())) {
            notificationService.rejectOrderStatusNotification(orders.getOrderId(), orders.getUser().getUserId());
        }
        return orderMapper.toOrderResponseDtoWithoutItems(orderRepository.save(orders));
    }

    @Override
    public Orders sendOrder(SendOrderRequest request) {
        Orders orders = orderRepository.findByOrderId(request.getOrderId());
        orders.setOrderStatus(OrderStatusEnum.PENDING_RES.getOrderStatus());
        orders.setOrderDeliveryAddress(request.getAddress());
        orders.setOrderUpdatedAt(LocalDateTime.now());
        return orderRepository.save(orders);
    }

    @Override
    public void deleteOrder(DeleteOrderRequest request) {
        Orders orders = orderRepository.findByOrderId(request.getOrderId());
        if(orders.getOrderStatus().equals(OrderStatusEnum.INIT.getOrderStatus())){
            orders.setOrderStatus(OrderStatusEnum.CANCEL.getOrderStatus());
            orderRepository.save(orders);
        }else {
            throw new AppException(AppError.ORDER_UPDATE_STATUS_INVALID);
        }
    }

    private Specification<Orders> buildSpecification(OrderQueryRequest request) {
        return (root, query, cb) -> null;
    }
}
