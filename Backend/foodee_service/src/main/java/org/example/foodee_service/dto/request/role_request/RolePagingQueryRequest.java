package org.example.foodee_service.dto.request.role_request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RolePagingQueryRequest extends PagingQueryRequest {
    private String name;
    private String description;
} 