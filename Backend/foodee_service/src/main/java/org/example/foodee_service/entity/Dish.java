package org.example.foodee_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodee_service.util.SecurityUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dishId;

    @Column(nullable = false)
    private String dishName;

    @Column(columnDefinition = "TEXT")
    private String dishDescription;

    @Column(precision = 10, scale = 2)
    private BigDecimal dishPriceValue;

    @Column(precision = 10, scale = 2)
    private BigDecimal dishSalePrice;

    @Column(length = 50)
    private String dishStatus;

    private String dishImageUrl;

    private LocalDateTime dishCreatedAt;

    private String dishCreatedBy;

    private LocalDateTime dishUpdatedAt;

    private String dishUpdatedBy;

    @ManyToOne
    private DishType dishType;

    @ManyToOne
    private Restaurant restaurant;

    @PrePersist
    public void prePersist() {
        dishCreatedAt = LocalDateTime.now();
        dishCreatedBy = SecurityUtil.getCurrentUsername();
    }

    @PreUpdate
    public void preUpdate() {
        dishUpdatedAt = LocalDateTime.now();
        dishUpdatedBy = SecurityUtil.getCurrentUsername();
    }
}

