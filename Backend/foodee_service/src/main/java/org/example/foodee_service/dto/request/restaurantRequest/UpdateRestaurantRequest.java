package org.example.foodee_service.dto.request.restaurantRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateRestaurantRequest {
    @NotNull
    private Long restaurantId;
    @NotBlank
    private String restaurantName;
    @NotBlank
    private String restaurantAddress;
    @NotBlank
    @Pattern(regexp = "^(\\+84|0)[0-9\\s-]{8,12}$", message = "Số điện thoại phải bắt đầu bằng 0 hoặc +84, theo sau là 8-12 chữ số")
    private String restaurantPhone;
    @NotBlank
    private String restaurantDescription;
    @NotNull
    private List<Long> categoryId;
}
