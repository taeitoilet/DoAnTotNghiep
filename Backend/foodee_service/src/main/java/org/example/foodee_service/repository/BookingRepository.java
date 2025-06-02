package org.example.foodee_service.repository;

import org.example.foodee_service.entity.Booking;
import org.example.foodee_service.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking,Long>, JpaSpecificationExecutor<Booking> {
    @Query("select b from Booking b where b.bookingId = :id")
    Booking findBookingById(Long id);
}
