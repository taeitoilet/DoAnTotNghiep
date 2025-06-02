package org.example.foodee_service.dto.request.restaurant_request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RestaurantNearByRequest extends PagingQueryRequest {
    Double latitude;
    Double longitude;
}
