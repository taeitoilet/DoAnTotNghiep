package org.example.foodee_service.dto.request.dish_request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateDishRequest {
    @NotNull
    private Long dishId;

    private String dishStatus;

    private String dishName;

    private String dishDescription;

    private Long dishTypeId;

    private BigDecimal dishPriceValue;

    private BigDecimal dishSalePrice;

}
