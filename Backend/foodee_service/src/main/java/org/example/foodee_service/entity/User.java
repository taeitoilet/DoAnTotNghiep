package org.example.foodee_service.entity;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(unique = true)
    private String userUsername;

    private String userFullName;
    
    @Column(unique = true)
    private String userEmail;

    private String userPhone;

    private String userPassword;

    private String userAvatarUrl;

    private LocalDateTime userCreatedAt;

    private String userCreatedBy;

    private LocalDateTime userUpdatedAt;

    private String userUpdatedBy;

    private String userStatus;

    @ManyToMany
    private Set<Role> roles;

    @PrePersist
    public void prePersist() {
        if (userCreatedAt == null) {
            userCreatedAt = LocalDateTime.now();
            userCreatedBy = "SYSTEM";
        }
    }

    @PreUpdate
    public void preUpdate() {
        userUpdatedAt = LocalDateTime.now();
        userUpdatedBy = "SYSTEM";
    }
}
