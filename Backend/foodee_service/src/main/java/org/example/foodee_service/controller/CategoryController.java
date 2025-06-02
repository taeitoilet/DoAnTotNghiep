package org.example.foodee_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.category_request.CategoryCreateRequest;
import org.example.foodee_service.dto.request.category_request.CategoryListQueryRequest;
import org.example.foodee_service.dto.request.category_request.CategoryUpdateRequest;
import org.example.foodee_service.dto.request.common.DetailQueryRequest;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.response.category_response.CategoryResponseDto;
import org.example.foodee_service.dto.response.common.Result;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.service.impl.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sys-management/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @Operation(summary = "Tạo danh mục mới", description = "Tạo danh mục mới", tags = {"Category"})
    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<CategoryResponseDto>> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryCreateRequest);
        ResultObject<CategoryResponseDto> resultObject = ResultObject.success(categoryResponseDto);
        resultObject.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(resultObject);
    }


    @PostMapping("/update/v1.0")
    public ResponseEntity<ResultObject<CategoryResponseDto>> updateCategory(@RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryUpdateRequest);
        ResultObject<CategoryResponseDto> response = ResultObject.success(categoryResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<Void>> deleteCategory(@RequestBody DetailQueryRequest request) {
        categoryService.deleteCategory(Long.valueOf(request.getId()));
        ResultObject<Void> result = ResultObject.success(null);
        result.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(result);
    }


    @PostMapping("/detail/v1.0")
    public ResponseEntity<ResultObject<CategoryResponseDto>> getCategoryDetail(@RequestBody @Valid DetailQueryRequest request) {
        CategoryResponseDto category = categoryService.getCategoryById(Long.valueOf(request.getId()));
        ResultObject<CategoryResponseDto> response = ResultObject.success(category);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<CategoryResponseDto>> getCategoryList(@RequestBody @Valid CategoryListQueryRequest request) {
        if (request.getPage() == null){
            request.setPage(new PagingQueryRequest.Page());
            request.getPage().setPageNum(1);
            request.getPage().setPageSize(10);
            request.getPage().setGetTotal(true);
        }

        Long[] total = new Long[1];
        List<CategoryResponseDto> categories = categoryService.getAllCategories(request, total);

        ResultList<CategoryResponseDto> response = null;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(categories, total[0]);
        } else {
            response = ResultList.success(categories);
        }
        response.setTraceId(UUID.randomUUID().toString());

        return ResponseEntity.ok(response);
    }

}
