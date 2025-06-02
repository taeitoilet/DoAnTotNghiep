package org.example.foodee_service.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.response.resTableResponse.ResTableResponseDto;
import org.example.foodee_service.entity.ResTable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResTableMapper {
    public ResTableResponseDto toResTableResponseDto(ResTable resTable) {
        return ResTableResponseDto.builder()
                .resTableId(resTable.getResTableId())
                .resTableIsAvailable(resTable.getResTableIsAvailable())
                .resTableName(resTable.getResTableName())
                .resTableSeats(resTable.getResTableSeats())
                .restaurantId(resTable.getRestaurant().getRestaurantId())
                .build();
    }
}
