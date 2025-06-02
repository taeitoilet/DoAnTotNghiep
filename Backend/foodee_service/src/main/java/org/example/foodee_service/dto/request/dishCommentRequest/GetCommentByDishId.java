package org.example.foodee_service.dto.request.dishCommentRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
@Data
public class GetCommentByDishId extends PagingQueryRequest {
    @NotNull
    private Long dishId;
}
