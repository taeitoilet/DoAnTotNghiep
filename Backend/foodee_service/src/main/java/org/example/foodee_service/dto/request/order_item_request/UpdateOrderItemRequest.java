package org.example.foodee_service.dto.request.order_item_request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderItemRequest {
    @NotNull
    Long orderItemId;
    Integer quantity;
    String orderItemNote;
}
