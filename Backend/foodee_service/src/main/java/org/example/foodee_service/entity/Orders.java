package org.example.foodee_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private BigDecimal orderTotalAmount;

    private String orderStatus;

    private String orderPaymentMethod;

    private String orderPaymentStatus;

    private String orderDeliveryAddress;

    private LocalDateTime orderCreatedAt;

    private String orderCreatedBy;

    private LocalDateTime orderUpdatedAt;

    private String orderUpdatedBy;

    private String userPhone;
    private String userName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @ManyToOne
    private User user;

    @ManyToOne
    private Restaurant restaurant;

    @PrePersist
    public void prePersist() {
        orderCreatedAt = LocalDateTime.now();
        orderCreatedBy = "SYSTEM";
    }

    @PreUpdate
    public void preUpdate() {
        orderUpdatedAt = LocalDateTime.now();
        orderUpdatedBy = "SYSTEM";
    }
}

