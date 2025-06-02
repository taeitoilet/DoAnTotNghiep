package org.example.foodee_service.dto.request.dishCommentRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class UpdateDishCommentRequest {
    @NotNull
    private Long DishCommentId;
    @NotBlank
    private String commentContent;
    private BigDecimal commentRating;
    private String commentCreateBy;
}
