package org.example.foodee_service.repository;

import org.example.foodee_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Modifying
    @Query("UPDATE Notification n SET n.notificationIsRead = true WHERE n.notificationId IN (:notificationIds)")
    void markAsRead(@Param("notificationIds") Long[] notificationIds);
}
