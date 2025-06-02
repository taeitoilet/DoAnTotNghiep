package org.example.foodee_service.mapper;

import org.example.foodee_service.dto.request.category_request.CategoryCreateRequest;
import org.example.foodee_service.dto.request.category_request.CategoryUpdateRequest;
import org.example.foodee_service.dto.response.category_response.CategoryResponseDto;
import org.example.foodee_service.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toCategory(CategoryCreateRequest categoryCreateRequest) {
        return Category.builder()
                .categoryName(categoryCreateRequest.getCategoryName())
                .categoryDescription(categoryCreateRequest.getCategoryDescription())
                .build();
    }

    public Category toCategory(CategoryUpdateRequest categoryUpdateRequest) {
        return Category.builder()
                .categoryId(categoryUpdateRequest.getCategoryId())
                .categoryName(categoryUpdateRequest.getCategoryName())
                .categoryDescription(categoryUpdateRequest.getCategoryDescription())
                .build();
    }

    public CategoryResponseDto toCategoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryDescription(category.getCategoryDescription())
                .build();
    }
}
