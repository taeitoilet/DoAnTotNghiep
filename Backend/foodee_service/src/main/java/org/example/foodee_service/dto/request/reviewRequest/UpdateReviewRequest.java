package org.example.foodee_service.dto.request.reviewRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class UpdateReviewRequest {
    @NotNull
    private Long reviewId;
    @NotBlank
    private String reviewComment;
    private BigDecimal reviewAvgRating;
    private Integer reviewLikes;
    private String reviewCreatedBy;
}
