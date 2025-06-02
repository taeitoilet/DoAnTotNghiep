package org.example.foodee_service.service.impl;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.request.userRequest.*;
import org.example.foodee_service.dto.response.userResponse.UserCreateResponseDto;
import org.example.foodee_service.dto.response.userResponse.UserRegisResponseDto;
import org.example.foodee_service.dto.response.userResponse.UserResponseDto;
import org.example.foodee_service.entity.Role;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.enums.UserStatusEnum;
import org.example.foodee_service.exception.AppError;
import org.example.foodee_service.exception.AppException;
import org.example.foodee_service.exception.ResourceNotFoundException;
import org.example.foodee_service.mapper.UserMapper;
import org.example.foodee_service.repository.RoleRepository;
import org.example.foodee_service.repository.UserRepository;
import org.example.foodee_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public String changeUserAvatar(UserChangeAvatarRequest request, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("user_avatar");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Xử lý tên file
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename().replace(" ", "_");
        Path filePath = uploadPath.resolve(fileName);
        log.info("File Path: {}", filePath);

        // Lưu file
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            try {
                if(request.getOldAvatarUrl() != null){
                    Path path = Paths.get(request.getOldAvatarUrl());
                    Files.deleteIfExists(path);// trả về true nếu file đã bị xóa
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(request.getUserId() != null && userRepository.existsById(request.getUserId())){
            User u = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));
            u.setUserAvatarUrl("http://localhost:8080/foodee/images/" + fileName);
            userRepository.save(u);
        }
        return "http://localhost:8080/foodee/images/" + fileName;
    }

    @Override
    @Transactional
    public UserRegisResponseDto registerUser(UserRegisRequest userRegisRequest) {
        if(userRepository.existsUserByUserUsername(userRegisRequest.getUserName())){
            throw new AppException(AppError.USERNAME_EXISTED);
        }else if(userRepository.existsUserByUserEmail(userRegisRequest.getUserEmail())){
            throw new AppException(AppError.USER_EMAIL_EXISTED);
        }else if(userRepository.existsUserByUserPhone(userRegisRequest.getUserPhone())){
            throw new AppException(AppError.USER_PHONE_EXISTED);
        }else{
            User user = User.builder()
                    .userUsername(userRegisRequest.getUserName())
                    .userFullName(userRegisRequest.getUserFullName())
                    .userEmail(userRegisRequest.getUserEmail())
                    .userPhone(userRegisRequest.getUserPhone())
                    .userPassword(passwordEncoder.encode(userRegisRequest.getUserPassword()))
                    .userStatus(UserStatusEnum.ACTIVE.getUserStatus())
                    .roles(roleRepository.getRoleByRoleName("USER"))
                    .userAvatarUrl("http://localhost:8080/foodee/images/default.png")
                    .build();
            return userMapper.toUserRegisResponseDto(userRepository.save(user));
        }
    }

    @Override
    public UserCreateResponseDto createUser(UserCreateRequest userCreateRequest) {
        if(userRepository.existsUserByUserUsername(userCreateRequest.getUserName())){
            throw new AppException(AppError.USERNAME_EXISTED);
        }else if(userRepository.existsUserByUserEmail(userCreateRequest.getUserEmail())){
            throw new AppException(AppError.USER_EMAIL_EXISTED);
        }else if(userRepository.existsUserByUserPhone(userCreateRequest.getUserPhone())){
            throw new AppException(AppError.USER_PHONE_EXISTED);
        }else{
            User user = User.builder()
                    .userUsername(userCreateRequest.getUserName())
                    .userFullName(userCreateRequest.getUserFullName())
                    .userEmail(userCreateRequest.getUserEmail())
                    .userPhone(userCreateRequest.getUserPhone())
                    .userPassword(userCreateRequest.getUserPassword() != null ? passwordEncoder.encode(userCreateRequest.getUserPassword()) : passwordEncoder.encode("Foodee@2025"))
                    .userStatus(UserStatusEnum.ACTIVE.getUserStatus().equals(userCreateRequest.getUserStatus()) ? UserStatusEnum.ACTIVE.getUserStatus() : UserStatusEnum.DISABLED.getUserStatus())
                    .roles(roleRepository.getRoleByRoleName("USER"))
                    .userAvatarUrl(userCreateRequest.getUserAvatarUrl())
                    .build();
            return userMapper.toUserCreateResponseDto(userRepository.save(user));
        }
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserById(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserByEmail() {
        return null;
    }

    @Override
    @Transactional
    public List<UserResponseDto> getAllUsers(UserQueryRequest request, long[] total) {

        Specification<User> spec = createSpecification(request);

        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );

        Page<User> usersPage;
        usersPage = userRepository.findAll(spec, pageable);

        usersPage.getContent().forEach(
                user -> {
                    if (user.getUserStatus().equals(UserStatusEnum.DELETED.getUserStatus())) {
                        usersPage.getContent().remove(user);
                    }
                }
        );

        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = usersPage.getTotalElements();
        }

        return usersPage.getContent().stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    @Override
    public void deleteUser(UserDeleteRequest request) {
        request.getUserId().forEach(userId -> {
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

            user.setUserStatus(UserStatusEnum.DELETED.getUserStatus());

            userRepository.save(user);
            log.info("User deleted: {}", userId);
        });
    }

    @Override
    public UserResponseDto updateUserBasicInfo(UserBasicUpdateRequest userBasicUpdateRequest) {
        User user = userRepository.findByUserId(userBasicUpdateRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userBasicUpdateRequest.getUserId()));

        if(userRepository.existsUserByUserUsername(userBasicUpdateRequest.getUsername()) && !user.getUserUsername().equals(userBasicUpdateRequest.getUsername())){
            throw new AppException(AppError.USERNAME_EXISTED);
        }else if(userRepository.existsUserByUserEmail(userBasicUpdateRequest.getUserEmail()) && !user.getUserEmail().equals(userBasicUpdateRequest.getUserEmail())){
            throw new AppException(AppError.USER_EMAIL_EXISTED);
        }else if(userRepository.existsUserByUserPhone(userBasicUpdateRequest.getUserPhone()) && !user.getUserPhone().equals(userBasicUpdateRequest.getUserPhone())){
            throw new AppException(AppError.USER_PHONE_EXISTED);
        }

        if (userBasicUpdateRequest.getUsername() != null) {
            user.setUserUsername(userBasicUpdateRequest.getUsername());
        }
        if (userBasicUpdateRequest.getUserFullName() != null) {
            user.setUserFullName(userBasicUpdateRequest.getUserFullName());
        }
        if (userBasicUpdateRequest.getUserEmail() != null) {
            user.setUserEmail(userBasicUpdateRequest.getUserEmail());
        }
        if (userBasicUpdateRequest.getUserPhone() != null) {
            user.setUserPhone(userBasicUpdateRequest.getUserPhone());
        }

        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public void updateUserPassword(UserPasswordUpdateRequest userPasswordUpdateRequest) {
        // TODO document why this method is empty
    }

    @Override
    public void updateUserStatus(UserStatusUpdateRequest userStatusUpdateRequest) {
        User user = userRepository.findByUserId(userStatusUpdateRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userStatusUpdateRequest.getUserId()));

        if (UserStatusEnum.isValid(userStatusUpdateRequest.getUserStatus())) {
            user.setUserStatus(userStatusUpdateRequest.getUserStatus());
        }else {
            throw new AppException(AppError.INVALID_USER_STATUS);
        }

        userRepository.save(user);
    }

    @Override
    public void updateUserRole(UserRoleUpdateRequest userRoleUpdateRequest) {
        User user = userRepository.findByUserId(userRoleUpdateRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userRoleUpdateRequest.getUserId()));

        user.getRoles().clear();

        userRoleUpdateRequest.getRoleName().forEach(roleName -> {
            Role role = roleRepository.findById(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", roleName));
            user.getRoles().add(role);
        });

        userRepository.save(user);
    }

    private Specification<User> createSpecification(UserQueryRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // Add direct filter predicates if provided
            if (request.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), request.getUserId()));
            }
            if (request.getUsername() != null && !request.getUsername().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("userUsername")), "%" + request.getUsername().toLowerCase() + "%"));
            }
            if (request.getFullName() != null && !request.getFullName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("userFullName")), "%" + request.getFullName().toLowerCase() + "%"));
            }
            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("userEmail")), "%" + request.getEmail().toLowerCase() + "%"));
            }
            if (request.getPhone() != null && !request.getPhone().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("userPhone")), "%" + request.getPhone().toLowerCase() + "%"));
            }
            if (request.getStatus() != null) {
                if (!request.getStatus().equals(UserStatusEnum.DELETED.getUserStatus())) {
                    predicates.add(cb.like(cb.lower(root.get("userStatus")), "%" + request.getStatus().toLowerCase() + "%"));
                }
            }else {
                predicates.add(cb.notLike(cb.lower(root.get("userStatus")), "%" + UserStatusEnum.DELETED.getUserStatus().toLowerCase() + "%"));
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
