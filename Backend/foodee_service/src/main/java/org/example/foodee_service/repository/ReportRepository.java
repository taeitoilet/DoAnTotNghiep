package org.example.foodee_service.repository;

import feign.Param;
import org.example.foodee_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Orders, Long> {
    @Query(value = """
    SELECT 
        CONCAT(TIME_FORMAT(ts.start_time, '%H:%i'), ' - ', TIME_FORMAT(ts.end_time, '%H:%i')) AS time_range,
        COALESCE(SUM(d.dish_sale_price * oi.order_item_quantity), 0) AS totalRevenue,
        COALESCE(SUM(oi.order_item_quantity), 0) AS totalQuantity,
        COALESCE(SUM(d.dish_sale_price * oi.order_item_quantity - d.dish_price_value * oi.order_item_quantity), 0) AS netRevenue
    FROM (
        SELECT TIME('00:00:00') AS start_time, TIME('03:00:00') AS end_time
        UNION ALL SELECT TIME('03:00:00'), TIME('06:00:00')
        UNION ALL SELECT TIME('06:00:00'), TIME('09:00:00')
        UNION ALL SELECT TIME('09:00:00'), TIME('12:00:00')
        UNION ALL SELECT TIME('12:00:00'), TIME('15:00:00')
        UNION ALL SELECT TIME('15:00:00'), TIME('18:00:00')
        UNION ALL SELECT TIME('18:00:00'), TIME('21:00:00')
        UNION ALL SELECT TIME('21:00:00'), TIME('23:59:59')
    ) AS ts
    LEFT JOIN orders o ON TIME(o.order_created_at) BETWEEN ts.start_time AND ts.end_time
                      AND DATE(o.order_created_at) = CURDATE()
                      AND o.restaurant_restaurant_id = :id
    LEFT JOIN order_item oi ON oi.orders_order_id = o.order_id
    LEFT JOIN dish d ON d.dish_id = oi.dish_dish_id
    GROUP BY ts.start_time, ts.end_time
    ORDER BY ts.start_time
    """, nativeQuery = true)
    List<Object[]> getDailyReport(Long id);

    @Query(value = """
    SELECT
        mr.month_number AS month,
        COALESCE(SUM(d.dish_sale_price * oi.order_item_quantity), 0) AS totalRevenue,
        COALESCE(SUM(oi.order_item_quantity), 0) AS totalQuantity,
        COALESCE(SUM(d.dish_sale_price * oi.order_item_quantity - d.dish_price_value * oi.order_item_quantity), 0) AS netRevenue
    FROM (
        SELECT 1 AS month_number UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
        UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8
        UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12
    ) AS mr
    LEFT JOIN orders o ON MONTH(o.order_created_at) = mr.month_number
                       AND YEAR(o.order_created_at) = YEAR(CURDATE()) 
                       AND o.restaurant_restaurant_id = :id
    LEFT JOIN order_item oi ON oi.orders_order_id = o.order_id
    LEFT JOIN dish d ON d.dish_id = oi.dish_dish_id
    GROUP BY mr.month_number
    ORDER BY mr.month_number
    """, nativeQuery = true)
    List<Object[]> getMonthlyReport(Long id);

    @Query(value = """
    SELECT
        dr.report_date AS day,
        COALESCE(SUM(d.dish_sale_price * oi.order_item_quantity), 0) AS totalRevenue,
        COALESCE(SUM(oi.order_item_quantity), 0) AS totalQuantity,
        COALESCE(SUM(d.dish_sale_price * oi.order_item_quantity - d.dish_price_value * oi.order_item_quantity), 0) AS netRevenue
    FROM (
        SELECT CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY AS report_date
        UNION ALL SELECT CURDATE()
        UNION ALL SELECT CURDATE() -  INTERVAL 1 DAY
        UNION ALL SELECT CURDATE() -  INTERVAL 2 DAY
        UNION ALL SELECT CURDATE() -  INTERVAL 3 DAY
        UNION ALL SELECT CURDATE() -  INTERVAL 4 DAY
        UNION ALL SELECT CURDATE() -  INTERVAL 5 DAY
        UNION ALL SELECT CURDATE() -  INTERVAL 6 DAY
        
    ) AS dr
    LEFT JOIN orders o ON DATE(o.order_created_at) = dr.report_date AND o.restaurant_restaurant_id = :id
    LEFT JOIN order_item oi ON oi.orders_order_id = o.order_id
    LEFT JOIN dish d ON d.dish_id = oi.dish_dish_id
    GROUP BY dr.report_date
    ORDER BY dr.report_date
    """, nativeQuery = true)
    List<Object[]> getWeeklyReport(Long id);

    @Query(value = """
    SELECT 
        d.dish_name AS dishName,
        SUM(oi.order_item_quantity) AS totalQuantity,
        SUM(oi.order_item_quantity * d.dish_sale_price) AS totalRevenue,
        d.dish_image_url AS imageUrl
    FROM orders o
    JOIN order_item oi ON oi.orders_order_id = o.order_id
    JOIN dish d ON d.dish_id = oi.dish_dish_id
    WHERE DATE(o.order_created_at) = CURDATE()
      AND o.restaurant_restaurant_id = :restaurantId
    GROUP BY d.dish_id, d.dish_name, d.dish_image_url
    ORDER BY totalQuantity DESC
    """, nativeQuery = true)
    List<Object[]> getDailyDishSales(Long restaurantId);

    @Query(value = """
    SELECT 
        d.dish_name AS dishName,
        SUM(oi.order_item_quantity) AS totalQuantity,
        SUM(oi.order_item_quantity * d.dish_sale_price) AS totalRevenue,
        d.dish_image_url AS imageUrl
    FROM orders o
    JOIN order_item oi ON oi.orders_order_id = o.order_id
    JOIN dish d ON d.dish_id = oi.dish_dish_id
    WHERE DATE(o.order_created_at) BETWEEN CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY 
                                     AND CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY + INTERVAL 6 DAY
      AND o.restaurant_restaurant_id = :restaurantId
    GROUP BY d.dish_id, d.dish_name, d.dish_image_url
    ORDER BY totalQuantity DESC
    """, nativeQuery = true)
    List<Object[]> getWeeklyDishSales(Long restaurantId);

    @Query(value = """
    SELECT 
        d.dish_name AS dishName,
        SUM(oi.order_item_quantity) AS totalQuantity,
        SUM(oi.order_item_quantity * d.dish_sale_price) AS totalRevenue,
        d.dish_image_url AS imageUrl
    FROM orders o
    JOIN order_item oi ON oi.orders_order_id = o.order_id
    JOIN dish d ON d.dish_id = oi.dish_dish_id
    WHERE MONTH(o.order_created_at) = MONTH(CURDATE())
      AND YEAR(o.order_created_at) = YEAR(CURDATE())
      AND o.restaurant_restaurant_id = :restaurantId
    GROUP BY d.dish_id, d.dish_name, d.dish_image_url
    ORDER BY totalQuantity DESC
    """, nativeQuery = true)
    List<Object[]> getMonthlyDishSales(Long restaurantId);

    @Query(value = """
    SELECT 
        d.dish_name AS dishName,
        SUM(oi.order_item_quantity) AS totalQuantity,
        SUM(oi.order_item_quantity * d.dish_sale_price) AS totalRevenue,
        d.dish_image_url AS imageUrl
    FROM orders o
    JOIN order_item oi ON oi.orders_order_id = o.order_id
    JOIN dish d ON d.dish_id = oi.dish_dish_id
    WHERE YEAR(o.order_created_at) = YEAR(CURDATE())
      AND o.restaurant_restaurant_id = :restaurantId
    GROUP BY d.dish_id, d.dish_name, d.dish_image_url
    ORDER BY totalQuantity DESC
    """, nativeQuery = true)
    List<Object[]> getYearlyDishSales(Long restaurantId);

    @Query(value = """
        SELECT 
        count(*) AS totalQuantity
    FROM orders o
    WHERE DATE(o.order_created_at) = CURDATE() 
        AND o.restaurant_restaurant_id = :restaurantId
    """, nativeQuery = true)
    Integer getDailyOrder(Long restaurantId);

     @Query(value = """
        SELECT 
        count(*) AS totalQuantity
        FROM orders o
        WHERE DATE(o.order_created_at) BETWEEN CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY
                                          AND CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY + INTERVAL 6 DAY
          AND o.restaurant_restaurant_id = :restaurantId                          
    """, nativeQuery = true)
     Integer  getWeeklyOrder(Long restaurantId);

     @Query(value = """
      SELECT 
        count(*) AS totalQuantity
        FROM orders o
      WHERE MONTH(o.order_created_at) = MONTH(CURDATE()) 
        AND YEAR(o.order_created_at) = YEAR(CURDATE()) 
        AND o.restaurant_restaurant_id = :restaurantId                                            
    """, nativeQuery = true)
     Integer  getMonthlyOrder(Long restaurantId);
    @Query(value = """
       SELECT 
        count(*) AS totalQuantity
    FROM orders o
       WHERE YEAR(o.order_created_at) = YEAR(CURDATE()) 
         AND o.restaurant_restaurant_id = :restaurantId                                           
    """, nativeQuery = true)
    Integer getYearlyOrder(Long restaurantId);

}
