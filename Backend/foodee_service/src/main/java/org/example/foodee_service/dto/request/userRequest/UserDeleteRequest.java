package org.example.foodee_service.dto.request.userRequest;

import lombok.Data;

import java.util.List;

@Data
public class UserDeleteRequest {
    private List<String> userId;
}
