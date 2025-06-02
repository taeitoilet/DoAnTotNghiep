package org.example.foodee_service.dto.request.resTableRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateResTableRequest {
    @NotBlank
    private String resTableName;
    @NotNull
    private Integer resTableSeats;
}
