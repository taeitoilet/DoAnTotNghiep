package org.example.foodee_service.dto.response.userResponse;

import lombok.*;
import org.example.foodee_service.entity.Role;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    UserResponseDto {
    private String userId;

    private String username;

    private String userFullName;

    private String userEmail;

    private String userPhone;

    private String userAvatarUrl;

    private String userStatus;

    private Set<Role> roles;

    private LocalDateTime userCreatedAt;

    private String userCreatedBy;

    private LocalDateTime userUpdatedAt;

    private String userUpdatedBy;
}
