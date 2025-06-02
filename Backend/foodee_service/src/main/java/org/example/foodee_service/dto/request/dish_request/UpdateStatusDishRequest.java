package org.example.foodee_service.dto.request.dish_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.foodee_service.enums.DishStatusEnum;

@Data
public class UpdateStatusDishRequest {
    @NotNull
    private Long dishId;
    @NotBlank
    private DishStatusEnum status;
}
