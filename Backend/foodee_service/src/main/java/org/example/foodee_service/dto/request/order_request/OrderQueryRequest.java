package org.example.foodee_service.dto.request.order_request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class OrderQueryRequest extends PagingQueryRequest {

}
