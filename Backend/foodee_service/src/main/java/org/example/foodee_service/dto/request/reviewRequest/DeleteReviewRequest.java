package org.example.foodee_service.dto.request.reviewRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteReviewRequest {
    @NotNull
    private Long reviewId;
}
