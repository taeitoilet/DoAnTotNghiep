package org.example.foodee_service.dto.request.userRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserQueryRequest extends PagingQueryRequest {
    String userId;
    String username;
    String fullName;
    String email;
    String phone;
    String status;
}
