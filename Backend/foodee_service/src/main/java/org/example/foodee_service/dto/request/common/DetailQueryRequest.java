package org.example.foodee_service.dto.request.common;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DetailQueryRequest {
    @NotBlank(message = "ID cannot be blank")
    //@Pattern(regexp = "^[A-Z_]+$", message = "Role name must contain only uppercase letters and underscores")
    String id;
}
