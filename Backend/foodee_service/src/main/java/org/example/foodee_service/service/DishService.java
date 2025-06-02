package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.dish_request.*;
import org.example.foodee_service.dto.request.restaurantRequest.GetIdRestaurantRequest;
import org.example.foodee_service.dto.response.dishResponse.DishResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DishService {

    List<DishResponseDto> getAllDishes(QueryDishRequest request,long[] total);

    List<DishResponseDto> getPendingDishes(QueryDishRequest request, long[] total);

    DishResponseDto getDishById(IdDishRequest request);

    DishResponseDto createDish(CreateDishRequest createRequest , MultipartFile image) throws IOException;

    DishResponseDto updateDish(UpdateDishRequest updateRequest, MultipartFile image) throws IOException;

    void deleteDish(DeleteDishRequest request);

    DishResponseDto switchDishStatus(UpdateStatusDishRequest request);

    List<DishResponseDto> getRestaurantDish(GetIdRestaurantRequest request, long[] total);
    List<DishResponseDto> getRestaurantDishV2(GetIdRestaurantRequest request, long[] total);
}
