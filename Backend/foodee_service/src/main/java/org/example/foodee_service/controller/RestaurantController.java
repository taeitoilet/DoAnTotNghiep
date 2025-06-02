package org.example.foodee_service.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.dish_request.CreateDishRequest;
import org.example.foodee_service.dto.request.restaurantRequest.CreateRestaurantRequest;
import org.example.foodee_service.dto.request.restaurantRequest.GetIdRestaurantRequest;
import org.example.foodee_service.dto.request.restaurantRequest.QueryRestaurantRequest;
import org.example.foodee_service.dto.request.restaurantRequest.UpdateRestaurantRequest;
import org.example.foodee_service.dto.request.restaurant_request.RestaurantNearByRequest;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.dto.response.dishResponse.DishResponseDto;
import org.example.foodee_service.dto.response.restaurantResponse.RestaurantResponseDto;
import org.example.foodee_service.dto.response.restaurant_response.RestaurantNearByResponseDto;
import org.example.foodee_service.service.RestaurantImportService;
import org.example.foodee_service.service.RestaurantService;

import org.example.foodee_service.dto.request.order_request.OrderQueryRequest;
import org.example.foodee_service.dto.request.restaurant_request.RestaurantQueryRequest;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.orderResponse.OrderResponseDto;
import org.example.foodee_service.service.RestaurantImportService;
import org.example.foodee_service.service.RestaurantService;
import org.example.foodee_service.service.impl.RestaurantServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sys-management/restaurant")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class RestaurantController {
    private final RestaurantImportService importService;

    private final RestaurantServiceImpl restaurantService;

    @PostMapping("/near-by/v1.0")
    public ResponseEntity<ResultList<RestaurantNearByResponseDto>> getNearBy(@RequestBody RestaurantNearByRequest request) {
        if (request.getPage() == null) {
            request.setPage(new OrderQueryRequest.Page());
            request.getPage().setPageNum(1);
            request.getPage().setPageSize(10);
            request.getPage().setGetTotal(true);
        }

        Long[] total = new Long[1];
        List<RestaurantNearByResponseDto> list = restaurantService.getNearbyRestaurants(request, total);
        ResultList<RestaurantNearByResponseDto> response = null;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(list, total[0]);
        } else {
            response = ResultList.success(list);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/import-data/v1.0")
    public ResponseEntity<String> importRestaurants(@RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        try {
            Long categoryId = Long.parseLong(filename.split("\\.")[0]); // Lấy từ tên file ví dụ "1.csv"
            importService.importFromCsv(file.getInputStream(), categoryId);
            return ResponseEntity.ok("Import thành công cho category ID: " + categoryId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Import lỗi: " + e.getMessage());
        }
    }
    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<RestaurantResponseDto>> createRes(@RequestPart(value = "request", required = true)
                                                                             @Valid
                                                                                     CreateRestaurantRequest request,
                                                                         @RequestPart("image") @Nullable MultipartFile image) throws IOException {
        RestaurantResponseDto restaurantResponseDto = restaurantService.createRestaurant(request,image);
        ResultObject<RestaurantResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/update/v1.0")
    public ResponseEntity<ResultObject<RestaurantResponseDto>> updateRes(@RequestPart(value = "request", required = true)
                                                                         @Valid
                                                                                     UpdateRestaurantRequest request,
                                                                         @RequestPart("image") @Nullable MultipartFile image) throws IOException {
        RestaurantResponseDto restaurantResponseDto = restaurantService.updateRestaurant(request,image);
        ResultObject<RestaurantResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/pending-res/v1.0")
    public ResponseEntity<ResultList<RestaurantResponseDto>> getPendingRes(@RequestBody QueryRestaurantRequest request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];


        List<RestaurantResponseDto> restaurants = restaurantService.getPendingRestaurant(request,total);
        ResultList<RestaurantResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(restaurants, total[0]);
        } else {
            response = ResultList.success(restaurants);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/active-res/v1.0")
    public ResponseEntity<ResultList<RestaurantResponseDto>> getActiveRes(@RequestBody QueryRestaurantRequest request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];

        List<RestaurantResponseDto> restaurants = restaurantService.getActiveRestaurant(request,total);
        ResultList<RestaurantResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(restaurants, total[0]);
        } else {
            response = ResultList.success(restaurants);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<RestaurantResponseDto>> getListRestaurant(@RequestBody RestaurantQueryRequest request){
        if (request.getPage() == null) {
            request.setPage(new OrderQueryRequest.Page());
            request.getPage().setPageNum(1);
            request.getPage().setPageSize(10);
            request.getPage().setGetTotal(true);
        }

        Long[] total = new Long[1];
        List<RestaurantResponseDto> orders = restaurantService.getListRestaurant(request, total);

        ResultList<RestaurantResponseDto> response = null;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(orders, total[0]);
        } else {
            response = ResultList.success(orders);

        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/detail/v1.0")
    public ResponseEntity<ResultObject<RestaurantResponseDto>> getDetail(@RequestBody @Valid GetIdRestaurantRequest request){
        RestaurantResponseDto restaurantResponseDto = restaurantService.getRestaurantById(request);
        ResultObject<RestaurantResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/inactive/v1.0")
    public ResponseEntity<ResultObject<RestaurantResponseDto>> inActiveRes(@RequestBody @Valid GetIdRestaurantRequest request){
        RestaurantResponseDto restaurantResponseDto = restaurantService.inActiveRestaurant(request);
        ResultObject<RestaurantResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/active/v1.0")
    public ResponseEntity<ResultObject<RestaurantResponseDto>> activeRes(@RequestBody @Valid GetIdRestaurantRequest request){
        RestaurantResponseDto restaurantResponseDto = restaurantService.activeRestaurant(request);
        ResultObject<RestaurantResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/is-booking/v1.0")
    public ResponseEntity<ResultObject<RestaurantResponseDto>> isBookingRes(@RequestBody @Valid GetIdRestaurantRequest request){
        RestaurantResponseDto restaurantResponseDto = restaurantService.setIsBookingRestaurant(request);
        ResultObject<RestaurantResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/is-open/v1.0")
    public ResponseEntity<ResultObject<RestaurantResponseDto>> isOpenRes(@RequestBody @Valid GetIdRestaurantRequest request){
        RestaurantResponseDto restaurantResponseDto = restaurantService.setIsOpenRestaurant(request);
        ResultObject<RestaurantResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<RestaurantResponseDto>> deleteRes(@RequestBody @Valid GetIdRestaurantRequest request){
        RestaurantResponseDto restaurantResponseDto = restaurantService.deleteRestaurant(request);
        ResultObject<RestaurantResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

}
