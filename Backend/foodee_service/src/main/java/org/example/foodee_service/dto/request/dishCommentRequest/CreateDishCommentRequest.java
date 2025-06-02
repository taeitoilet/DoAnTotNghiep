package org.example.foodee_service.dto.request.dishCommentRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateDishCommentRequest {
    @NotBlank
    private String commentContent;
    private BigDecimal commentRating;
    private String commentCreateBy;
    private Long dishId;
    private String userId;
}
