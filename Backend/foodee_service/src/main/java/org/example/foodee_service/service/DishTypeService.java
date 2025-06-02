package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.dishTypeRequest.CreateDishTypeRequest;
import org.example.foodee_service.dto.request.dishTypeRequest.DeleteDishTypeRequest;
import org.example.foodee_service.dto.request.dishTypeRequest.QueryDishTypeRequest;
import org.example.foodee_service.entity.DishType;

import java.util.List;

public interface DishTypeService {
    List<DishType> getAllDishTypes(QueryDishTypeRequest request, long[] total);
    DishType createDishType(CreateDishTypeRequest request);
    void deleteDishType(DeleteDishTypeRequest request);
}
