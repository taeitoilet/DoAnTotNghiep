package org.example.foodee_service.repository;

import org.example.foodee_service.entity.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish,Long>, JpaSpecificationExecutor<Dish> {

    @Query("SELECT d from Dish d where d.dishId = :id")
    Dish findByDishId(Long id);

    @Query("select d from Dish d " +
            "where d.restaurant.restaurantId = :id " +
            "and (:dishTypeId IS NULL OR d.dishType.dishTypeId = :dishTypeId) " +
            "order by d.dishType.dishTypeId")
    Page<Dish> findDishByRestaurantIdPaging(@Param("id") Long id,
                                            @Param("dishTypeId") Long dishTypeId,
                                            Pageable pageable);

    @Query("select d from Dish d where d.restaurant.restaurantId = :id order by d.dishType.dishTypeId")
    List<Dish> findDishByRestaurantId(Long id);
}
