package org.example.foodee_service.dto.request.dishTypeRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDishTypeRequest {
    @NotBlank
    String dishTypeName;
    String dishTypeDes;
}
