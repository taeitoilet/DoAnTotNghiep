package org.example.foodee_service.repository;

import org.example.foodee_service.entity.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DishTypeRepository extends JpaRepository<DishType,Long>, JpaSpecificationExecutor<DishType> {
    @Query("select dt from DishType dt where dt.dishTypeId = :id")
    DishType findDishTypeById(Long id);
}
