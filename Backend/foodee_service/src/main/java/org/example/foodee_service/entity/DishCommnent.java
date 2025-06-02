package org.example.foodee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodee_service.util.SecurityUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishCommnent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentContent;
    private BigDecimal commentRating;

    private LocalDateTime commentCreateAt;
    private String commentCreateBy;

    private LocalDateTime commnentUpdateAt;
    private String commentUpdateBy;

    @ManyToOne
    private Dish dish;

    @ManyToOne
    private User user;

    @PrePersist
    public void prePersist() {
        commentCreateAt = LocalDateTime.now();
        commentCreateBy = SecurityUtil.getCurrentUsername();
    }

    @PreUpdate
    public void preUpdate() {
        commnentUpdateAt = LocalDateTime.now();
        commentUpdateBy = SecurityUtil.getCurrentUsername();
    }
}
