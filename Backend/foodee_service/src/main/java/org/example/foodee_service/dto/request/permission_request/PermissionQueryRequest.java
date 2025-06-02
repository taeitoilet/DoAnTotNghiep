package org.example.foodee_service.dto.request.permission_request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.foodee_service.dto.request.common.QueryRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PermissionQueryRequest extends QueryRequest {
    private String name;
    private String description;
} 