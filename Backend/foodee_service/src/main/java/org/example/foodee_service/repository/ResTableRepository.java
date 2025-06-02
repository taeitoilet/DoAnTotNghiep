package org.example.foodee_service.repository;

import org.example.foodee_service.entity.ResTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResTableRepository extends JpaRepository<ResTable, Long> {
    @Query("select rt from ResTable rt where rt.resTableId = :id")
    ResTable getResTableById(Long id);
    @Query("select rt from ResTable rt where rt.restaurant.restaurantId = :id")
    Page<ResTable> findAll(Long id, Pageable pageable);
}
