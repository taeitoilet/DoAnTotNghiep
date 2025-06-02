package org.example.foodee_service.dto.request.userRequest;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRoleUpdateRequest {
    private String userId;
    private List<String> roleName;
}
