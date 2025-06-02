package org.example.foodee_service.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.dishTypeRequest.CreateDishTypeRequest;
import org.example.foodee_service.dto.request.dishTypeRequest.DeleteDishTypeRequest;
import org.example.foodee_service.dto.request.dishTypeRequest.QueryDishTypeRequest;
import org.example.foodee_service.entity.DishType;
import org.example.foodee_service.repository.DishTypeRepository;
import org.example.foodee_service.service.DishTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishTypeServiceImpl implements DishTypeService {
    private final DishTypeRepository dishTypeRepository;

    @Override
    public List<DishType> getAllDishTypes(QueryDishTypeRequest request, long[] total) {
        Specification<DishType> spec = createSpecification(request);
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<DishType> dishTypePage;
        dishTypePage = dishTypeRepository.findAll(spec,pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = dishTypePage.getTotalElements();
        }
        return dishTypePage.getContent().stream().toList();
    }

    @Override
    public DishType createDishType(CreateDishTypeRequest request) {
        DishType dishType = DishType.builder()
                .dishTypeName(request.getDishTypeName())
                .dishTypeDescription(request.getDishTypeDes())
                .dishTypeCreatedAt(LocalDateTime.now())
                .dishTypeUpdatedAt(LocalDateTime.now())
                .build();
        return dishTypeRepository.save(dishType);
    }

    @Override
    public void deleteDishType(DeleteDishTypeRequest request) {
        dishTypeRepository.deleteById(request.getDishTypeId());
    }


    private Specification<DishType> createSpecification(QueryDishTypeRequest request){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(request.getDishTypeName() != null){
                predicates.add(criteriaBuilder.like(root.get("dishTypeName"),"%" + request.getDishTypeName() + "%"));
            }
            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
