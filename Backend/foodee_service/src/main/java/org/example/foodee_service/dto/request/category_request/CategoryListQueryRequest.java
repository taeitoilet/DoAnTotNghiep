package org.example.foodee_service.dto.request.category_request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class CategoryListQueryRequest extends PagingQueryRequest {
}
