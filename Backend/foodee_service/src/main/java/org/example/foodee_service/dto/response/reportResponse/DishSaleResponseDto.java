package org.example.foodee_service.dto.response.reportResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishSaleResponseDto {
    private String dishName;
    private Long totalQuantity;
    private Double totalRevenue;
    private String imageUrl;
}
