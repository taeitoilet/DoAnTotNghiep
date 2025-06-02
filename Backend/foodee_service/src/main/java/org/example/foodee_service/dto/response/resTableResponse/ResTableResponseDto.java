package org.example.foodee_service.dto.response.resTableResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResTableResponseDto {
    private Long resTableId;

    private String resTableName;

    private Integer resTableSeats;

    private Boolean resTableIsAvailable;
    private Long restaurantId;
}
