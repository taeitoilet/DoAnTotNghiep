package org.example.foodee_service.dto.request.userRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class UserBasicUpdateRequest {
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must contain only alphanumeric letters and underscores")
    @Size(min = 5, max = 50, message = "Role name must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    private String userFullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String userEmail;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\d{9,15}$", message = "Phone number must contain only digits and be between 9 to 15 characters")
    private String userPhone;
}
