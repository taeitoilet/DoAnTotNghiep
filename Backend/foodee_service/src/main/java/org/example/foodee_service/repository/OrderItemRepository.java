package org.example.foodee_service.repository;

import org.example.foodee_service.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long>, JpaSpecificationExecutor<OrderItem> {
    @Query("SELECT o from OrderItem o where o.orderItemId = :id")
    OrderItem findByOrderItemId(Long id);

    @Query("SELECT o from OrderItem o where o.orders.orderId = :orderId")
    List<OrderItem> findOrderItemByOrderId(Long orderId);

    Page<OrderItem> findAllByOrders_OrderId(Pageable pageable, Long orderId);
}
