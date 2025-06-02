package org.example.foodee_service.repository;

import org.example.foodee_service.entity.DishCommnent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishCommentRepository extends JpaRepository<DishCommnent,Long>, JpaSpecificationExecutor<DishCommnent> {
    @Query("SELECT c from DishCommnent c where c.commentId = :id")
    DishCommnent findByDishCommentId(Long id);
}
