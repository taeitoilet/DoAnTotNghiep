package org.example.foodee_service.dto.request.order_request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodee_service.enums.OrderStatusEnum;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
    @NotNull
    Long orderId;
    @NotNull
    @Enumerated(EnumType.STRING)
    OrderStatusEnum orderStatus;
}
