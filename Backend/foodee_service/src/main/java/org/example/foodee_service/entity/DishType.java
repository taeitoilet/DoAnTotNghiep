package org.example.foodee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodee_service.util.SecurityUtil;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dishTypeId;

    private String dishTypeName;

    private String dishTypeDescription;

    private LocalDateTime dishTypeCreatedAt;

    private String dishTypeCreatedBy;

    private LocalDateTime dishTypeUpdatedAt;

    private String dishTypeUpdatedBy;

    @PrePersist
    public void prePersist() {
        dishTypeCreatedAt = LocalDateTime.now();
        dishTypeCreatedBy = SecurityUtil.getCurrentUsername();
    }

    @PreUpdate
    public void preUpdate() {
        dishTypeUpdatedAt = LocalDateTime.now();
        dishTypeUpdatedBy = SecurityUtil.getCurrentUsername();
    }
}

