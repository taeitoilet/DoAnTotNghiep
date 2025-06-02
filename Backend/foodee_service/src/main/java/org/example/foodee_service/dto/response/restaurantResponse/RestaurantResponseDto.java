package org.example.foodee_service.dto.response.restaurantResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponseDto {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;

    private String restaurantPhone;

    private BigDecimal restaurantAvgRating;

    private String restaurantAvgRatingText;

    private String restaurantPhotoUrl;
    private String restaurantDescription;

    private List<String> categoryName;
    private Boolean restaurantIsAds;
    private Boolean restaurantIsDelivery;
    private Boolean restaurantIsOpening;
    private Integer restaurantStatus;
    private Double restaurantLatitude;
    private Double restaurantLongitude;
    private Boolean restaurantIsBooking;
    private double distance;
}
