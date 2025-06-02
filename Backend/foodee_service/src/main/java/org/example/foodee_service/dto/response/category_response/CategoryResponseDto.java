package org.example.foodee_service.dto.response.category_response;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryResponseDto {
    private Long categoryId;

    private String categoryName;

    private String categoryDescription;

    private LocalDateTime categoryCreatedAt;

    private String categoryCreatedBy;

    private LocalDateTime categoryUpdatedAt;

    private String categoryUpdatedBy;
}
