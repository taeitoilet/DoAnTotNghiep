package org.example.foodee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.client.NotiServiceClient;
import org.example.foodee_service.dto.request.common.DetailQueryRequest;
import org.example.foodee_service.dto.request.order_request.DeleteOrderRequest;
import org.example.foodee_service.dto.request.order_request.OrderCreateRequest;
import org.example.foodee_service.dto.request.order_request.OrderQueryRequest;
import org.example.foodee_service.dto.request.order_request.UpdateOrderRequest;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.dto.response.orderResponse.OrderResponseDto;
import org.example.foodee_service.service.impl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/food-order/order")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderServiceImpl orderService;
    private final NotiServiceClient notiServiceClient;
    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<OrderResponseDto>> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        OrderResponseDto orders = orderService.createOrder(request);
        ResultObject<OrderResponseDto> response = ResultObject.success(orders);
        response.setTraceId(UUID.randomUUID().toString());
//        notiServiceClient.sendNotification("123", "You have a new order");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/v1.0")
    public ResponseEntity<ResultObject<OrderResponseDto>> updateOrder(@RequestBody @Valid UpdateOrderRequest request) {
        OrderResponseDto orders = orderService.updateOrder(request);
        ResultObject<OrderResponseDto> response = ResultObject.success(orders);
        response.setTraceId(UUID.randomUUID().toString());
//        notiServiceClient.sendNotification("123", "You have a new order");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<OrderResponseDto>> listOrder(@RequestBody @Valid OrderQueryRequest request) {
        if (request.getPage() == null) {
            request.setPage(new OrderQueryRequest.Page());
            request.getPage().setPageNum(1);
            request.getPage().setPageSize(10);
            request.getPage().setGetTotal(true);
        }

        Long[] total = new Long[1];
        List<OrderResponseDto> orders = orderService.getAllOrders(request, total);

        ResultList<OrderResponseDto> response = null;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(orders, total[0]);
        } else {
            response = ResultList.success(orders);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
   @PostMapping("/pending-user-order/v1.0")
    public ResponseEntity<ResultList<OrderResponseDto>> listOrderByUser(@RequestBody @Valid OrderQueryRequest request) {
        if (request.getPage() == null) {
            request.setPage(new OrderQueryRequest.Page());
            request.getPage().setPageNum(1);
            request.getPage().setPageSize(10);
            request.getPage().setGetTotal(true);
        }

        Long[] total = new Long[1];
        List<OrderResponseDto> orders = orderService.getOrdersByUser(request, total);

        ResultList<OrderResponseDto> response = null;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(orders, total[0]);
        } else {
            response = ResultList.success(orders);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/user-order/v1.0")
    public ResponseEntity<ResultList<OrderResponseDto>> listAllOrderByUser(@RequestBody @Valid OrderQueryRequest request) {
        if (request.getPage() == null) {
            request.setPage(new OrderQueryRequest.Page());
            request.getPage().setPageNum(1);
            request.getPage().setPageSize(10);
            request.getPage().setGetTotal(true);
        }

        Long[] total = new Long[1];
        List<OrderResponseDto> orders = orderService.getAllOrdersByUser(request, total);

        ResultList<OrderResponseDto> response = null;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(orders, total[0]);
        } else {
            response = ResultList.success(orders);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/list-pending/v1.0")
    public ResponseEntity<ResultList<OrderResponseDto>> listPendingOrder(@RequestBody @Valid OrderQueryRequest request) {
        if (request.getPage() == null) {
            request.setPage(new OrderQueryRequest.Page());
            request.getPage().setPageNum(1);
            request.getPage().setPageSize(10);
            request.getPage().setGetTotal(true);
        }

        Long[] total = new Long[1];
        List<OrderResponseDto> orders = orderService.getPendingOrders(request, total);

        ResultList<OrderResponseDto> response = null;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(orders, total[0]);
        } else {
            response = ResultList.success(orders);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/detail/v1.0")
    public ResponseEntity<ResultObject<OrderResponseDto>> getOrderDetail(@RequestBody @Valid DetailQueryRequest request) {
        OrderResponseDto order = orderService.getOrderbyId(Long.valueOf(request.getId()));
        ResultObject<OrderResponseDto> response = ResultObject.success(order);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
        @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<String>> deleteOrder(@RequestBody @Valid DeleteOrderRequest request) {
        orderService.deleteOrder(request);
        ResultObject<String> response = ResultObject.success("Xóa thành công");
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

}
