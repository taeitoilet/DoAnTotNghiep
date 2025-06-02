package org.example.foodee_service.dto.request.reviewRequest;

import lombok.Data;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
@Data
public class GetReviewById extends PagingQueryRequest {
    private Long restauranId;
}
