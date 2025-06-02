package org.example.foodee_service.dto.request.dishTypeRequest;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteDishTypeRequest {
    Long dishTypeId;
}
