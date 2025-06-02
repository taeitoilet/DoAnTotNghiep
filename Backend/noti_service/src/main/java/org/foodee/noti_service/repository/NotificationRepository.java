package org.foodee.noti_service.repository;

import org.foodee.noti_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.userId = :userId and n.notificationCreatedAt >= :time")
    List<Notification> findByUserId(@Param("userId") String userId, @Param("time") LocalDateTime time);
}
