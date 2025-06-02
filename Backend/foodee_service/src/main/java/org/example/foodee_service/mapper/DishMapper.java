package org.example.foodee_service.mapper;

import org.example.foodee_service.dto.response.dishResponse.DishResponseDto;
import org.example.foodee_service.entity.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

    public DishResponseDto toDishResponseDto(Dish dish){
        return DishResponseDto.builder()
                .dishId(dish.getDishId())
                .dishName(dish.getDishName())
                .dishDescription(dish.getDishDescription())
                .dishPriceValue(dish.getDishPriceValue())
                .dishStatus(dish.getDishStatus())
                .dishImageUrl(dish.getDishImageUrl())
                .dishTypeName(dish.getDishType().getDishTypeName())
                .restaurant(dish.getRestaurant() != null ? dish.getRestaurant().getRestaurantName() : null)
                .dishSalePrice(dish.getDishSalePrice())
                .restaurantId(dish.getRestaurant() != null ? dish.getRestaurant().getRestaurantId(): null)
                .build();
    }
}
