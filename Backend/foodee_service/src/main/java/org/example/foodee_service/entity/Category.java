package org.example.foodee_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.foodee_service.util.SecurityUtil;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    private String categoryDescription;

    private LocalDateTime categoryCreatedAt;

    private String categoryCreatedBy;

    private LocalDateTime categoryUpdatedAt;

    private String categoryUpdatedBy;
    
    @PrePersist
    public void prePersist() {
        categoryCreatedAt = LocalDateTime.now();
        categoryCreatedBy = SecurityUtil.getCurrentUsername();
    }

    @PreUpdate
    public void preUpdate() {
        categoryUpdatedAt = LocalDateTime.now();
        categoryUpdatedBy = SecurityUtil.getCurrentUsername();
    }
}

