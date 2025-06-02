package org.example.foodee_service.dto.request.dishCommentRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteDishCommentRequest {
    @NotNull
    private Long dishCommentId;
}
