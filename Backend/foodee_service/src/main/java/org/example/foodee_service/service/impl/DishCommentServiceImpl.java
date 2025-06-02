package org.example.foodee_service.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.request.dishCommentRequest.CreateDishCommentRequest;
import org.example.foodee_service.dto.request.dishCommentRequest.DeleteDishCommentRequest;
import org.example.foodee_service.dto.request.dishCommentRequest.GetCommentByDishId;
import org.example.foodee_service.dto.request.dishCommentRequest.UpdateDishCommentRequest;
import org.example.foodee_service.dto.response.comment.DishCommentResponseDto;
import org.example.foodee_service.entity.Dish;
import org.example.foodee_service.entity.DishCommnent;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.mapper.CommentMapper;
import org.example.foodee_service.repository.DishCommentRepository;
import org.example.foodee_service.repository.DishRepository;
import org.example.foodee_service.repository.UserRepository;
import org.example.foodee_service.service.DishCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishCommentServiceImpl implements DishCommentService {
    private final DishRepository dishRepository;
    private final DishCommentRepository dishCommentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    @Override
    public List<DishCommentResponseDto> getAllComment(GetCommentByDishId request, long[] total) {
        Specification<DishCommnent> spec = createSpecification(request);
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<DishCommnent> commentPage = dishCommentRepository.findAll(spec, pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = commentPage.getTotalElements();
        }
        return commentPage.getContent().stream().map(commentMapper::toDishCommentResponseDto).toList();
    }

    @Override
    public DishCommentResponseDto createComment(CreateDishCommentRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByUserId(jwt.getClaimAsString("userId"));
        Dish dish = dishRepository.findByDishId(request.getDishId());
        DishCommnent dishCommnent = DishCommnent.builder()
                .commentCreateBy(request.getCommentCreateBy())
                .commentCreateAt(LocalDateTime.now())
                .commentContent(request.getCommentContent())
                .commentRating(request.getCommentRating())
                .dish(dish)
                .user(user)
                .commnentUpdateAt(LocalDateTime.now())
                .build();
        return commentMapper.toDishCommentResponseDto(dishCommentRepository.save(dishCommnent));
    }

    @Override
    public DishCommentResponseDto updateComment(UpdateDishCommentRequest request) {
        DishCommnent dishCommnent = dishCommentRepository.findByDishCommentId(request.getDishCommentId());
        dishCommnent.setCommentContent(request.getCommentContent());
        dishCommnent.setCommentCreateBy(request.getCommentCreateBy());
        dishCommnent.setCommentRating(request.getCommentRating());
        dishCommnent.setCommnentUpdateAt(LocalDateTime.now());
        return commentMapper.toDishCommentResponseDto(dishCommentRepository.save(dishCommnent));
    }

    @Override
    public void deleteComment(DeleteDishCommentRequest request) {
        dishCommentRepository.deleteById(request.getDishCommentId());
    }

    private Specification<DishCommnent> createSpecification(GetCommentByDishId request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(request.getDishId() != null){
                predicates.add(criteriaBuilder.equal(root.get("dish").get("dishId"),request.getDishId()));
            }
            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
