package org.example.foodee_service.dto.request.dish_request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteDishRequest {
    @NotNull
    private Long[] dishId;
}
