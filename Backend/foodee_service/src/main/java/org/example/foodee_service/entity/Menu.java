package org.example.foodee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodee_service.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {

    @Id
    private Long menuId;

    private LocalDateTime menuCreatedAt;

    private String menuCreatedBy;

    private LocalDateTime menuUpdatedAt;

    private String menuUpdatedBy;

    @ManyToOne
    private Restaurant menuRestaurant;

    @OneToMany
    private List<Dish> dishes;

    @OneToMany
    private List<DishType> dishTypes;

    @PrePersist
    public void prePersist() {
        menuCreatedAt = LocalDateTime.now();
        menuCreatedBy = SecurityUtil.getCurrentUsername();
    }

    @PreUpdate
    public void preUpdate() {
        menuUpdatedAt = LocalDateTime.now();
        menuUpdatedBy = SecurityUtil.getCurrentUsername();
    }
}

