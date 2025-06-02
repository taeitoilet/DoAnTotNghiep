package org.example.foodee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.dishTypeRequest.CreateDishTypeRequest;
import org.example.foodee_service.dto.request.dishTypeRequest.DeleteDishTypeRequest;
import org.example.foodee_service.dto.request.dishTypeRequest.QueryDishTypeRequest;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.entity.DishType;
import org.example.foodee_service.service.DishTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sys-management/dish-type")
@RequiredArgsConstructor
@Validated
public class DishTypeController {
    private final DishTypeService dishTypeService;

    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<DishType>> createDishType(@RequestBody @Valid CreateDishTypeRequest request){
        DishType dishType = dishTypeService.createDishType(request);
        ResultObject<DishType> response = ResultObject.success(dishType);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<DishType>> getAllDishType(@RequestBody @Valid QueryDishTypeRequest request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];
        List<DishType> list = dishTypeService.getAllDishTypes(request,total);
        ResultList<DishType> response ;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(list, total[0]);
        } else {
            response = ResultList.success(list);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<String>> deleteDishType(@RequestBody DeleteDishTypeRequest request){
        dishTypeService.deleteDishType(request);
        ResultObject<String> response = ResultObject.success("Xoa thanh cong !");
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
}
