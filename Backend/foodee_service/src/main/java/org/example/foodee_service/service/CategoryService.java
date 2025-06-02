package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.category_request.CategoryCreateRequest;
import org.example.foodee_service.dto.request.category_request.CategoryListQueryRequest;
import org.example.foodee_service.dto.request.category_request.CategoryUpdateRequest;
import org.example.foodee_service.dto.response.category_response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponseDto updateCategory(CategoryUpdateRequest categoryUpdateRequest);

    void deleteCategory(Long categoryId);

    CategoryResponseDto getCategoryById(Long categoryId);

    List<CategoryResponseDto> getAllCategories(CategoryListQueryRequest categoryListQueryRequest, Long[] total);

}
