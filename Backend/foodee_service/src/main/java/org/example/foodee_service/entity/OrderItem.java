package org.example.foodee_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private Integer orderItemQuantity;

    private BigDecimal orderItemPrice;

    private String orderItemNote;

    private LocalDateTime orderItemCreatedAt;

    private String orderItemCreatedBy;

    private LocalDateTime orderItemUpdatedAt;

    private String orderItemUpdatedBy;

    @ManyToOne
    private Dish dish;

    @ManyToOne
    @JsonBackReference
    private Orders orders;

    @PrePersist
    public void prePersist() {
        orderItemCreatedAt = LocalDateTime.now();
        orderItemCreatedBy = "SYSTEM";
    }

    @PreUpdate
    public void preUpdate() {
        orderItemUpdatedAt = LocalDateTime.now();
        orderItemUpdatedBy = "SYSTEM";
    }
}
