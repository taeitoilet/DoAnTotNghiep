package org.example.foodee_service.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String reviewComment;

    private BigDecimal reviewAvgRating;

    private Integer reviewLikes;

    private LocalDateTime reviewCreatedAt;

    private String reviewCreatedBy;

    private LocalDateTime reviewUpdatedAt;

    private String reviewUpdatedBy;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private User user;
}

