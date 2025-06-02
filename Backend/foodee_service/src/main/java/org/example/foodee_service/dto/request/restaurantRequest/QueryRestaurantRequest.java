package org.example.foodee_service.dto.request.restaurantRequest;

import lombok.Data;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;

import java.util.List;

@Data
public class QueryRestaurantRequest extends PagingQueryRequest {
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhone;
    private List<Long> categoryId;
    private Integer restaurantStatus;
    private String ownerName;
}
