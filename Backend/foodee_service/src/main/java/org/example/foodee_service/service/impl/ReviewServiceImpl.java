package org.example.foodee_service.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.request.reviewRequest.CreateReviewRequest;
import org.example.foodee_service.dto.request.reviewRequest.DeleteReviewRequest;
import org.example.foodee_service.dto.request.reviewRequest.GetReviewById;
import org.example.foodee_service.dto.request.reviewRequest.UpdateReviewRequest;
import org.example.foodee_service.dto.response.review.ReviewResponseDto;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.entity.Review;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.mapper.ReviewMapper;
import org.example.foodee_service.repository.RestaurantRepository;
import org.example.foodee_service.repository.ReviewRepository;
import org.example.foodee_service.repository.UserRepository;
import org.example.foodee_service.service.ReviewService;
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
public class ReviewServiceImpl implements ReviewService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;

    @Override
    public List<ReviewResponseDto> getAllReview(GetReviewById request, long[] total) {
        Specification<Review> spec = createSpecification(request);

        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<Review> reviewPage = reviewRepository.findAll(spec,pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = reviewPage.getTotalElements();
        }
        return reviewPage.getContent().stream().map(reviewMapper::toReviewResponseDto).toList();
    }

    @Override
    public ReviewResponseDto createReview(CreateReviewRequest request) {
        Restaurant restaurant = restaurantRepository.findByRestaurantId(request.getRestaurantId());
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByUserId(jwt.getClaimAsString("userId"));
        Review review = Review.builder()
                .reviewAvgRating(request.getReviewAvgRating())
                .reviewCreatedAt(LocalDateTime.now())
                .reviewUpdatedAt(LocalDateTime.now())
                .reviewCreatedBy(request.getReviewCreatedBy())
                .reviewComment(request.getReviewComment())
                .reviewLikes(request.getReviewLikes())
                .restaurant(restaurant)
                .user(user)
                .build();
        return reviewMapper.toReviewResponseDto(reviewRepository.save(review));
    }

    @Override
    public ReviewResponseDto updateReview(UpdateReviewRequest request) {
        Review review = reviewRepository.findReviewById(request.getReviewId());
        review.setReviewComment(request.getReviewComment());
        review.setReviewAvgRating(request.getReviewAvgRating());
        review.setReviewCreatedBy(request.getReviewCreatedBy());
        review.setReviewUpdatedAt(LocalDateTime.now());
        review.setReviewLikes(request.getReviewLikes());
        return reviewMapper.toReviewResponseDto(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(DeleteReviewRequest request) {
        reviewRepository.deleteById(request.getReviewId());
    }

    private Specification<Review> createSpecification(GetReviewById request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(request.getRestauranId() != null){
                predicates.add(criteriaBuilder.equal(root.get("restaurant").get("restaurantId"),request.getRestauranId()));
            }
            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
