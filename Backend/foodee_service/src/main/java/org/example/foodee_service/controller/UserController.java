package org.example.foodee_service.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.common.DetailQueryRequest;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.userRequest.*;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.dto.response.userResponse.UserCreateResponseDto;
import org.example.foodee_service.dto.response.userResponse.UserRegisResponseDto;
import org.example.foodee_service.dto.response.userResponse.UserResponseDto;
import org.example.foodee_service.service.impl.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authorization/auth/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/register/v1.0")
    public ResponseEntity<ResultObject<UserRegisResponseDto>> registerUser(
            @RequestBody
            @Valid
            UserRegisRequest userRegisRequest) {
        UserRegisResponseDto user = userService.registerUser(userRegisRequest);
        ResultObject<UserRegisResponseDto> response = ResultObject.success(user);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<UserCreateResponseDto>> createUser(
            @RequestBody
            @Valid
            UserCreateRequest userCreateRequest) {
        UserCreateResponseDto user = userService.createUser(userCreateRequest);
        ResultObject<UserCreateResponseDto> response = ResultObject.success(user);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<UserResponseDto>> getUserList(@RequestBody @Valid UserQueryRequest request) {
        // Check default values for paging
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }

        long[] total = new long[1];

        List<UserResponseDto> user = userService.getAllUsers(request, total);
        ResultList<UserResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(user, total[0]);
        } else {
            response = ResultList.success(user);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/my-info/v1.0")
    public ResponseEntity<ResultObject<UserResponseDto>> getMyInfo() {
        var context = SecurityContextHolder.getContext();
        UserResponseDto user = userService.getUserByUsername(context.getAuthentication().getName());
        ResultObject<UserResponseDto> response = ResultObject.success(user);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/detail/v1.0")
    public ResponseEntity<ResultObject<UserResponseDto>> getUserDetail(@RequestBody @Valid DetailQueryRequest request) {
        UserResponseDto user = userService.getUserById(request.getId());
        ResultObject<UserResponseDto> response = ResultObject.success(user);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/v1.0")
    public ResponseEntity<ResultObject<UserResponseDto>> updateUser(@RequestBody @Valid UserBasicUpdateRequest request) {
        UserResponseDto user = userService.updateUserBasicInfo(request);
        ResultObject<UserResponseDto> response = ResultObject.success(user);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/status/v1.0")
    public ResponseEntity<ResultObject<UserResponseDto>> updateUserStatus(@RequestBody @Valid UserStatusUpdateRequest request) {
        userService.updateUserStatus(request);
        ResultObject<UserResponseDto> response = ResultObject.success(null);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/role/v1.0")
    public ResponseEntity<ResultObject<UserResponseDto>> updateUserRole(@RequestBody @Valid UserRoleUpdateRequest request) {
        userService.updateUserRole(request);
        ResultObject<UserResponseDto> response = ResultObject.success(null);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<Void>> deleteUser(@RequestBody @Valid UserDeleteRequest request) {
        userService.deleteUser(request);
        ResultObject<Void> response = ResultObject.success(null);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/upload-avatar/v1.0", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultObject<String>> uploadAvatar(
            @RequestPart(value = "request", required = true)
            @Valid UserChangeAvatarRequest request,
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        String url = userService.changeUserAvatar(request, file);
        ResultObject<String> response = ResultObject.success(url);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
}
