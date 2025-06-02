package org.example.foodee_service.dto.response.restaurant_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantNearByResponseDto {
    private Long restaurantId;

    private String restaurantName;

    private String restaurantAddress;

//    private String restaurantPhone;

    private String restaurantAvgRatingText;

//    private String restaurantPhotoUrl;
//
//    private Double restaurantLatitude;
//
//    private Double restaurantLongitude;
//
//    private String restaurantDescription;

//    private Integer restaurantStatus;
//
//    private Boolean restaurantIsOpening;

//    private Boolean restaurantIsAds;
//
//    private Boolean restaurantIsBooking;

    private Boolean restaurantIsDelivery;

    private String userId;

    private double distance;
}
