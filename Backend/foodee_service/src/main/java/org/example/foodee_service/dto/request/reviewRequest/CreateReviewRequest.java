package org.example.foodee_service.dto.request.reviewRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateReviewRequest {
    @NotBlank
    private String reviewComment;
    private BigDecimal reviewAvgRating;
    private Integer reviewLikes;
    private String reviewCreatedBy;
    private Long restaurantId;
    private String userId;
}
