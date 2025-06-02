package org.example.foodee_service.repository;

import org.example.foodee_service.entity.Dish;
import org.example.foodee_service.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

    @Query("SELECT r from Restaurant r where r.restaurantId = :id")
    Restaurant findByRestaurantId(Long id);
    @Query("SELECT r from Restaurant r where r.owner.userId = :id")
    Restaurant findByUserId(String id);

    Restaurant getByRestaurantId(Long restaurantId);

    Boolean existsRestaurantByRestaurantPhone(String phone);
    @Query("SELECT r from Restaurant r where r.restaurantPhone = :phone")
    List<Restaurant> findByRestaurantPhone(String phone);
//
//   Restaurant getByRestaurantId(Long restaurantId);
//    @Query("SELECT r FROM Restaurant r WHERE " +
//            "(6371 * acos(cos(radians(:user_lat)) * cos(radians(r.restaurantLatitude)) * cos(radians(r.restaurantLongitude) - radians(:user_lon)) + sin(radians(:user_lat)) * sin(radians(r.restaurantLatitude)))) <= 3")
//    List<Restaurant> findNearbyRestaurants(@Param("userLat") double userLat, @Param("userLon") double userLon);

//    // Native SQL query để tính khoảng cách sử dụng công thức Haversine
//    @Query(value = "SELECT r.restaurant_id, r.restaurant_name, r.restaurant_address, r.restaurant_avg_rating_text, r.owner_user_id, " +
//            "(6371 * acos(cos(radians(:user_lat)) * cos(radians(r.restaurant_latitude)) * cos(radians(r.restaurant_longitude) - radians(:user_lon)) + " +
//            "sin(radians(:user_lat)) * sin(radians(r.restaurant_latitude)))) AS distance " +
//            "FROM restaurant r " +
//            "WHERE r.restaurant_status = 2 AND r.restaurant_is_opening = true " +
//            "HAVING distance <= :radius " +
//            "ORDER BY distance", nativeQuery = true)
//    Page<Object[]> findNearbyRestaurants(@Param("user_lat") double userLat,
//                                         @Param("user_lon") double userLon,
//                                         @Param("radius") double radius,
//                                         Pageable pageable);

    @Query(value = "SELECT r.restaurant_id, r.restaurant_name, r.restaurant_address, r.restaurant_avg_rating_text, r.owner_user_id, " +
            "(6371 * acos(cos(radians(:user_lat)) * cos(radians(r.restaurant_latitude)) * cos(radians(r.restaurant_longitude) - radians(:user_lon)) + " +
            "sin(radians(:user_lat)) * sin(radians(r.restaurant_latitude)))) AS distance " +
            "FROM restaurant r " +
            "WHERE r.restaurant_status = 2 " +
            "AND r.restaurant_is_opening = true " +
            "AND (6371 * acos(cos(radians(:user_lat)) * cos(radians(r.restaurant_latitude)) * cos(radians(r.restaurant_longitude) - radians(:user_lon)) + " +
            "sin(radians(:user_lat)) * sin(radians(r.restaurant_latitude)))) <= :radius " +
            "ORDER BY distance", nativeQuery = true)
    Page<Object[]> findNearbyRestaurants(@Param("user_lat") double userLat,
                                         @Param("user_lon") double userLon,
                                         @Param("radius") double radius,
                                         Pageable pageable);

}
