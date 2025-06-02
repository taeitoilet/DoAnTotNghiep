package org.example.foodee_service.dto.request.userRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatusUpdateRequest {
    private String userId;
    private String userStatus;
}
