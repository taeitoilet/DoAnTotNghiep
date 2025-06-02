package org.example.foodee_service.service;


import org.example.foodee_service.dto.request.restaurantRequest.CreateRestaurantRequest;
import org.example.foodee_service.dto.request.restaurantRequest.GetIdRestaurantRequest;
import org.example.foodee_service.dto.request.restaurantRequest.QueryRestaurantRequest;
import org.example.foodee_service.dto.request.restaurantRequest.UpdateRestaurantRequest;
import org.example.foodee_service.dto.response.restaurantResponse.RestaurantResponseDto;
import org.example.foodee_service.dto.response.restaurantResponse.RevenueRestaurantResponseDto;
import org.example.foodee_service.dto.request.restaurant_request.RestaurantQueryRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RestaurantService {
    public List<RestaurantResponseDto> getListRestaurant(RestaurantQueryRequest request, Long[] total);
    List<RestaurantResponseDto> getAllRestaurant(QueryRestaurantRequest request, long[] total);
    List<RestaurantResponseDto> getActiveRestaurant(QueryRestaurantRequest request, long[] total);
    List<RestaurantResponseDto> getPendingRestaurant(QueryRestaurantRequest request, long[] total);
    RestaurantResponseDto getRestaurantById(GetIdRestaurantRequest request);
    RestaurantResponseDto createRestaurant(CreateRestaurantRequest request, MultipartFile image ) throws IOException;
    RestaurantResponseDto updateRestaurant(UpdateRestaurantRequest request, MultipartFile image) throws IOException;
    RestaurantResponseDto inActiveRestaurant(GetIdRestaurantRequest request);
    RestaurantResponseDto activeRestaurant(GetIdRestaurantRequest request);
    RestaurantResponseDto deleteRestaurant(GetIdRestaurantRequest request);
    RevenueRestaurantResponseDto getRevenue();
    RestaurantResponseDto setIsOpenRestaurant(GetIdRestaurantRequest request);
    RestaurantResponseDto setIsBookingRestaurant(GetIdRestaurantRequest request);


}
