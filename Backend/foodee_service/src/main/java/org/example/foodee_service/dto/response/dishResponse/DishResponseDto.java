package org.example.foodee_service.dto.response.dishResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodee_service.entity.DishType;
import org.example.foodee_service.entity.Restaurant;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishResponseDto {
    private Long dishId;
    private String dishName;

    private String dishDescription;

    private BigDecimal dishPriceValue;

    private String dishStatus;

    private String dishImageUrl;

    private String dishTypeName;

    private String restaurant;

    private BigDecimal dishSalePrice;
    private Long restaurantId;
}
