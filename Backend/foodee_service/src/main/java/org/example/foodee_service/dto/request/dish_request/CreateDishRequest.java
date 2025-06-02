package org.example.foodee_service.dto.request.dish_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateDishRequest {
    @NotBlank
    private String dishName;
    @NotBlank
    private String dishDescription;
    @Positive
    private BigDecimal dishPriceValue;
    @NotNull
    private Long dishTypeId;
    @NotNull
    private Long restaurantId;
    @NotNull
    private BigDecimal dishSalePrice;

    private String dishStatus;
}
