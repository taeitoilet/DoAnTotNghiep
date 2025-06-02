package org.example.foodee_service.mapper;


import org.example.foodee_service.dto.response.restaurantResponse.RestaurantResponseDto;
import org.example.foodee_service.entity.Category;
import org.example.foodee_service.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import org.example.foodee_service.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant){
        return RestaurantResponseDto.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantAddress(restaurant.getRestaurantAddress())
                .restaurantPhone(restaurant.getRestaurantPhone())
                .restaurantAvgRating(restaurant.getRestaurantAvgRating())
                .restaurantDescription(restaurant.getRestaurantDescription())
                .restaurantPhotoUrl(restaurant.getRestaurantPhotoUrl())
                .restaurantAvgRatingText(restaurant.getRestaurantAvgRatingText())
                .categoryName(restaurant.getCategory().stream()
                        .map(Category::getCategoryName)
                        .collect(Collectors.toList()))

                .restaurantName(restaurant.getRestaurantName())
                .restaurantIsAds(restaurant.getRestaurantIsAds())
                .restaurantIsDelivery(restaurant.getRestaurantIsDelivery())
                .restaurantIsOpening(restaurant.getRestaurantIsOpening())
                .restaurantStatus(restaurant.getRestaurantStatus())
                .restaurantLatitude(restaurant.getRestaurantLatitude())
                .restaurantLongitude(restaurant.getRestaurantLongitude())
                .restaurantIsBooking(restaurant.getRestaurantIsBooking())
                .restaurantDescription(restaurant.getRestaurantDescription())
                .build();
    }
    public RestaurantResponseDto toRestaurantDistanceResponseDto(Restaurant restaurant, double distance){
        return RestaurantResponseDto.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantAddress(restaurant.getRestaurantAddress())
                .restaurantPhone(restaurant.getRestaurantPhone())
                .restaurantAvgRating(restaurant.getRestaurantAvgRating())
                .restaurantDescription(restaurant.getRestaurantDescription())
                .restaurantPhotoUrl(restaurant.getRestaurantPhotoUrl())
                .restaurantAvgRatingText(restaurant.getRestaurantAvgRatingText())
                .categoryName(restaurant.getCategory().stream()
                        .map(Category::getCategoryName)
                        .collect(Collectors.toList()))

                .restaurantName(restaurant.getRestaurantName())
                .restaurantIsAds(restaurant.getRestaurantIsAds())
                .restaurantIsDelivery(restaurant.getRestaurantIsDelivery())
                .restaurantIsOpening(restaurant.getRestaurantIsOpening())
                .restaurantStatus(restaurant.getRestaurantStatus())
                .restaurantLatitude(restaurant.getRestaurantLatitude())
                .restaurantLongitude(restaurant.getRestaurantLongitude())
                .restaurantIsBooking(restaurant.getRestaurantIsBooking())
                .restaurantDescription(restaurant.getRestaurantDescription())
                .distance(distance)
                .build();
    }
}
