package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.userRequest.*;
import org.example.foodee_service.dto.response.userResponse.UserCreateResponseDto;
import org.example.foodee_service.dto.response.userResponse.UserRegisResponseDto;
import org.example.foodee_service.dto.response.userResponse.UserResponseDto;

import java.util.List;

public interface UserService {
    UserRegisResponseDto registerUser(UserRegisRequest userRegisRequest);

    UserCreateResponseDto createUser(UserCreateRequest userCreateRequest);

    UserResponseDto getUserByUsername(String username);

    UserResponseDto getUserById(String userId);

    UserResponseDto getUserByEmail();

    List<UserResponseDto> getAllUsers(UserQueryRequest request, long[] total);

    void deleteUser(UserDeleteRequest request);

    UserResponseDto updateUserBasicInfo(UserBasicUpdateRequest userBasicUpdateRequest);

    void updateUserPassword(UserPasswordUpdateRequest userPasswordUpdateRequest);

    void updateUserStatus(UserStatusUpdateRequest userStatusUpdateRequest);

    void updateUserRole(UserRoleUpdateRequest userRoleUpdateRequest);
}
