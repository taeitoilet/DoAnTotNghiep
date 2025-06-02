package org.example.foodee_service.dto.response.restaurantResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueRestaurantResponseDto {
    private BigInteger sales;
    private BigDecimal revenue;
}
