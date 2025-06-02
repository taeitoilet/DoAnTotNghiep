package org.example.foodee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {

    @Id
    private Long cartId;

    private Integer cartQuantity;

    private LocalDateTime cartCreatedAt;

    private String cartCreatedBy;

    private LocalDateTime cartUpdatedAt;

    private String cartUpdatedBy;

    @ManyToOne
    private User user;

    @ManyToOne
    private Dish dish;

}

