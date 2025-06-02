package org.example.foodee_service.util;

import lombok.AllArgsConstructor;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SecurityUtil {

    private final UserRepository userRepository;

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName(); // Trả về username từ JWT
    }

    public User getCurrentUser() {
        return userRepository.findByUserUsername(getCurrentUsername()).orElse(null);
    }
}
