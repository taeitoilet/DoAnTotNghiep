package org.example.foodee_service.dto.request.role_request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
public class RoleUpdateRequest {
    
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
    
    private Set<String> permissionNames;
    
    // Operation type for permissions: ADD, REMOVE, REPLACE
    private PermissionOperation permissionOperation;
    
    public enum PermissionOperation {
        ADD,        // Add new permissions to existing ones
        REMOVE,     // Remove specified permissions
        REPLACE     // Replace all permissions with new ones
    }
} 