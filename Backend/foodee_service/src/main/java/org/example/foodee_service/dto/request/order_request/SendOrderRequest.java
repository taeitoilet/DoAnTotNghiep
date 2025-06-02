package org.example.foodee_service.dto.request.order_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendOrderRequest {
    @NotNull
    private Long orderId;
    private String pmtMethod;
    @NotBlank
    private String address;

}
