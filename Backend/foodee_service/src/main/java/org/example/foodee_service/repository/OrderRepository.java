package org.example.foodee_service.repository;

import org.example.foodee_service.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    @Query("SELECT o from Orders o where o.orderId = :id")
    Orders findByOrderId(Long id);
    @Query("SELECT o from Orders o where o.user.userId = :id")
    Page<Orders> findByUserId(String id,Pageable pageable);
    @Query("SELECT o from Orders o where o.restaurant.restaurantId = :id order by o.orderCreatedAt desc ")
    Page<Orders> findAllOrders(Long id,Pageable pageable);
    @Query("SELECT o from Orders o where o.restaurant.restaurantId = :id and o.orderStatus = 'INIT' order by o.orderCreatedAt desc")
    Page<Orders> findPendingOrders(Long id,Pageable pageable);

    @Query("SELECT o from Orders o where o.user.userId = :id and o.orderStatus = 'INIT' order by o.orderCreatedAt desc")
    Page<Orders> findOrdersByUser(String id,Pageable pageable);
    @Query("SELECT o from Orders o where o.user.userId = :id  order by o.orderCreatedAt desc")
    Page<Orders> findAllOrdersByUser(String id,Pageable pageable);

    Page<Orders> findAll(Pageable pageable);
}
