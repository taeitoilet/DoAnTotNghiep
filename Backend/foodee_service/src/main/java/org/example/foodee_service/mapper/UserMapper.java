package org.example.foodee_service.mapper;

import org.example.foodee_service.dto.request.userRequest.UserCreateRequest;
import org.example.foodee_service.dto.response.userResponse.UserCreateResponseDto;
import org.example.foodee_service.dto.response.userResponse.UserRegisResponseDto;
import org.example.foodee_service.dto.response.userResponse.UserResponseDto;
import org.example.foodee_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserRegisResponseDto toUserRegisResponseDto(User user) {
        return UserRegisResponseDto.builder()
                .userName(user.getUserUsername())
                .userFullName(user.getUserFullName())
                .userEmail(user.getUserEmail())
                .userPhone(user.getUserPhone())
                .userStatus(user.getUserStatus())
                .build();
    }

    public UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .username(user.getUserUsername())
                .userFullName(user.getUserFullName())
                .userAvatarUrl(user.getUserAvatarUrl())
                .userEmail(user.getUserEmail())
                .userPhone(user.getUserPhone())
                .userStatus(user.getUserStatus())
                .roles(user.getRoles())
                .userCreatedBy(user.getUserCreatedBy())
                .userCreatedAt(user.getUserCreatedAt())
                .userUpdatedBy(user.getUserUpdatedBy())
                .userUpdatedAt(user.getUserUpdatedAt())
                .build();
    }

    public UserCreateResponseDto toUserCreateResponseDto(User user) {
        return UserCreateResponseDto.builder()
                .userName(user.getUserUsername())
                .userFullName(user.getUserFullName())
                .userEmail(user.getUserEmail())
                .userPhone(user.getUserPhone())
                .userStatus(user.getUserStatus())
                .build();
    }
}
