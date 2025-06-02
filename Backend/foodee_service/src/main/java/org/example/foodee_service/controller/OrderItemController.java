package org.example.foodee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.order_request.OrderItemQueryRequest;
import org.example.foodee_service.dto.request.order_request.OrderQueryRequest;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.orderItemResponse.OrderItemResponseDto;
import org.example.foodee_service.service.impl.OrderItemServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/food-order/order-items")
@RequiredArgsConstructor
@Validated
public class OrderItemController {
    private final OrderItemServiceImpl orderItemService;

    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<OrderItemResponseDto>> listOrder(@RequestBody @Valid OrderItemQueryRequest request) {
        if (request.getPage() == null) {
            request.setPage(new OrderQueryRequest.Page());
            request.getPage().setPageNum(1);
            request.getPage().setPageSize(10);
            request.getPage().setGetTotal(true);
        }

        Long[] total = new Long[1];
        List<OrderItemResponseDto> orders = orderItemService.getAllOrderItems(request, total);

        ResultList<OrderItemResponseDto> response = null;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(orders, total[0]);
        } else {
            response = ResultList.success(orders);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
}
