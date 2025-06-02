package org.example.foodee_service.dto.request.role_request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.foodee_service.dto.request.common.QueryRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class RoleQueryRequest extends QueryRequest {
    private String name;
    private String description;
} 