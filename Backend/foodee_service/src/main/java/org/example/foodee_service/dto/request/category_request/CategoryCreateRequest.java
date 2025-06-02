package org.example.foodee_service.dto.request.category_request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryCreateRequest {
    private String categoryName;
    private String categoryDescription;
}
