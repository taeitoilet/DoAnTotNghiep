package org.example.foodee_service.dto.response.roleResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodee_service.dto.response.permissionResponse.PermissionResponseDto;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RoleResponseDto {
    private String roleName;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<PermissionResponseDto> permissions;
} 