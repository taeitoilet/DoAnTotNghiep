package org.example.foodee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.dish_request.IdDishRequest;
import org.example.foodee_service.dto.request.resTableRequest.CreateResTableRequest;
import org.example.foodee_service.dto.request.resTableRequest.GetIdResTableRequest;
import org.example.foodee_service.dto.request.resTableRequest.UpdateResTableRequest;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.dto.response.dishResponse.DishResponseDto;
import org.example.foodee_service.dto.response.resTableResponse.ResTableResponseDto;
import org.example.foodee_service.dto.response.restaurantResponse.RestaurantResponseDto;
import org.example.foodee_service.service.ResTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/table")
@RequiredArgsConstructor
@Validated
public class ResTableController {
    private final ResTableService resTableService;

    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<ResTableResponseDto>> create(@Valid @RequestBody CreateResTableRequest request) {
        ResTableResponseDto restaurantResponseDto = resTableService.createResTable(request);
        ResultObject<ResTableResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/update/v1.0")
    public ResponseEntity<ResultObject<ResTableResponseDto>> update(@Valid @RequestBody UpdateResTableRequest request) {
        ResTableResponseDto restaurantResponseDto = resTableService.updateResTable(request);
        ResultObject<ResTableResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<ResTableResponseDto>> getAllResTables(@Valid @RequestBody PagingQueryRequest request) {
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];

        List<ResTableResponseDto> resTableResponseDtos = resTableService.getResTable(request,total);
        ResultList<ResTableResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(resTableResponseDtos, total[0]);
        } else {
            response = ResultList.success(resTableResponseDtos);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<String>> delete(@RequestBody @Valid GetIdResTableRequest request){
        resTableService.deleteResTable(request);
        ResultObject<String> response = ResultObject.success("Xoa thanh cong !");
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/is-available/v1.0")
    public ResponseEntity<ResultObject<ResTableResponseDto>> isAvailable(@RequestBody @Valid  GetIdResTableRequest request) {
        ResTableResponseDto restaurantResponseDto = resTableService.isAvailableResTable(request);
        ResultObject<ResTableResponseDto> response = ResultObject.success(restaurantResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
}
