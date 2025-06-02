package org.example.foodee_service.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.dish_request.*;
import org.example.foodee_service.dto.request.restaurantRequest.GetIdRestaurantRequest;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.dto.response.dishResponse.DishResponseDto;
import org.example.foodee_service.service.DishService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
@Validated
public class DishController {
    private final DishService dishService;

    @PostMapping(value = "/create/v1.0", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultObject<DishResponseDto>> createDish(
            @RequestPart(value = "request", required = true)
            @Valid
                    CreateDishRequest request,
            @RequestPart("image") @Nullable MultipartFile image) throws IOException {

        DishResponseDto dishResponseDto = dishService.createDish(request, image);
        ResultObject<DishResponseDto> response = ResultObject.success(dishResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/update/v1.0", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultObject<DishResponseDto>> updateDish(
            @RequestPart(value = "request", required = true)
            @Valid
                    UpdateDishRequest request,
            @RequestPart("image") @Nullable MultipartFile image) throws IOException {

        DishResponseDto dishResponseDto = dishService.updateDish(request, image);
        ResultObject<DishResponseDto> response = ResultObject.success(dishResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<DishResponseDto>> getAllDishes(@RequestBody @Valid QueryDishRequest request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];

        List<DishResponseDto> dishes = dishService.getAllDishes(request,total);
        ResultList<DishResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(dishes, total[0]);
        } else {
            response = ResultList.success(dishes);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/res-dishes/v1.0")
    public ResponseEntity<ResultList<DishResponseDto>> getRestaurantDishes(@RequestBody @Valid GetIdRestaurantRequest request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];

        List<DishResponseDto> dishes = dishService.getRestaurantDish(request,total);
        ResultList<DishResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(dishes, total[0]);
        } else {
            response = ResultList.success(dishes);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/res-dishes/v2.0")
    public ResponseEntity<ResultList<DishResponseDto>> getRestaurantDishesV2(@RequestBody @Valid GetIdRestaurantRequest request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];

        List<DishResponseDto> dishes = dishService.getRestaurantDishV2(request,total);
        ResultList<DishResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(dishes, total[0]);
        } else {
            response = ResultList.success(dishes);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/detail/v1.0")
    public ResponseEntity<ResultObject<DishResponseDto>> getDetail(@RequestBody @Valid IdDishRequest request){
        DishResponseDto dish = dishService.getDishById(request);
        ResultObject<DishResponseDto> response = ResultObject.success(dish);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/list/pending/v1.0")
    public ResponseEntity<ResultList<DishResponseDto>> getPendingList(@RequestBody @Valid QueryDishRequest request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];

        List<DishResponseDto> dishes = dishService.getPendingDishes(request,total);
        ResultList<DishResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(dishes, total[0]);
        } else {
            response = ResultList.success(dishes);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/switch-status/v1.0")
    public ResponseEntity<ResultObject<DishResponseDto>> switchDishStatus(@RequestBody @Valid UpdateStatusDishRequest request){
        DishResponseDto dish = dishService.switchDishStatus(request);
        ResultObject<DishResponseDto> response = ResultObject.success(dish);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<String>> deleteDish(@RequestBody @Valid DeleteDishRequest request){
        dishService.deleteDish(request);
        ResultObject<String> response = ResultObject.success("Xoa thanh cong!");
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

}
