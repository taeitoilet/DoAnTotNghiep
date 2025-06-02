package org.example.foodee_service.dto.response.userResponse;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
//@NoArgsConstructor
//@RequiredArgsConstructor
@Builder
public class UserRegisResponseDto {
    private String userName;

    private String userFullName;

    private String userEmail;

    private String userPhone;

    private String userStatus;
}
