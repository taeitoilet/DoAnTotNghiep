package org.example.foodee_service.dto.request.category_request;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryUpdateRequest {
    private Long categoryId;

    private String categoryName;

    private String categoryDescription;
}
