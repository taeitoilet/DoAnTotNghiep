package org.example.foodee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private String restaurantName;

    private String restaurantAddress;

    private String restaurantPhone;

    private BigDecimal restaurantAvgRating;

    private String restaurantAvgRatingText;

    private String restaurantPhotoUrl;

    private Double restaurantLatitude;

    private Double restaurantLongitude;

    private String restaurantDescription;

    private Integer restaurantStatus;

    private Boolean restaurantIsOpening;

    private Boolean restaurantIsAds;

    private Boolean restaurantIsBooking;

    private Boolean restaurantIsDelivery;

    private LocalDateTime restaurantCreatedAt;

    private String restaurantCreatedBy;

    private LocalDateTime restaurantUpdatedAt;

    private String restaurantUpdatedBy;

    @OneToOne
    private User owner;
    
    @ManyToMany
    private Set<Category> category;
//
//    @Column(columnDefinition = "Geometry")
//    private Point location;

    @PrePersist
    public void prePersist() {
        restaurantCreatedAt = LocalDateTime.now();
        restaurantCreatedBy = "SYSTEM";
    }

    @PreUpdate
    public void preUpdate() {
        restaurantUpdatedAt = LocalDateTime.now();
        restaurantUpdatedBy = "SYSTEM";
    }
}

