package org.example.foodee_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.category_request.CategoryCreateRequest;
import org.example.foodee_service.dto.request.category_request.CategoryListQueryRequest;
import org.example.foodee_service.dto.request.category_request.CategoryUpdateRequest;
import org.example.foodee_service.dto.response.category_response.CategoryResponseDto;
import org.example.foodee_service.entity.Category;
import org.example.foodee_service.exception.ResourceNotFoundException;
import org.example.foodee_service.mapper.CategoryMapper;
import org.example.foodee_service.repository.CategoryRepository;
import org.example.foodee_service.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto createCategory(CategoryCreateRequest categoryCreateRequest) {
        Category category = categoryMapper.toCategory(categoryCreateRequest);

        return categoryMapper.toCategoryResponseDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryUpdateRequest categoryUpdateRequest) {
        if (!categoryRepository.existsCategoryByCategoryId(categoryUpdateRequest.getCategoryId())){
            throw new ResourceNotFoundException("Category", "categoryId", categoryUpdateRequest.getCategoryId());
        }
        Category category = categoryMapper.toCategory(categoryUpdateRequest);
        return categoryMapper.toCategoryResponseDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsCategoryByCategoryId(categoryId)){
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        return categoryMapper.toCategoryResponseDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories(CategoryListQueryRequest categoryListQueryRequest, Long[] total) {
        Pageable pageable = PageRequest.of(categoryListQueryRequest.getPage().getPageNum() - 1, categoryListQueryRequest.getPage().getPageSize());

        Page<Category> categories = categoryRepository.findAll(pageable);


        if (Boolean.TRUE.equals(categoryListQueryRequest.getPage().getGetTotal())) {
            total[0] = categories.getTotalElements();
        }
        return categories.stream().map(categoryMapper::toCategoryResponseDto).toList();
    }
}
