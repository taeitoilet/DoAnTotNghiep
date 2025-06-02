package org.example.foodee_service.dto.request.restaurantRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetIdRestaurantRequest extends PagingQueryRequest {
    @NotNull
    private Long restaurantId;

    private Long dishTypeId;
}
