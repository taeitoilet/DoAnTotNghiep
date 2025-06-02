package org.example.foodee_service.dto.response.userResponse;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
//@NoArgsConstructor
//@RequiredArgsConstructor
@Builder
public class UserCreateResponseDto {
    private String userName;

    private String userFullName;

    private String userEmail;

    private String userPhone;

    private String userStatus;
}
