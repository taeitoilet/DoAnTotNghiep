package org.example.foodee_service.dto.request.permission_request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionUpdateRequest {
    
    @NotBlank(message = "Permission description is required")
    private String description;
} 