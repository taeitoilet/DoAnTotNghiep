package org.example.foodee_service.mapper;

import org.example.foodee_service.dto.response.review.ReviewResponseDto;
import org.example.foodee_service.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponseDto toReviewResponseDto(Review review){
        return ReviewResponseDto.builder()
                .reviewAvgRating(review.getReviewAvgRating())
                .reviewComment(review.getReviewComment())
                .reviewId(review.getReviewId())
                .reviewCreatedAt(review.getReviewCreatedAt())
                .reviewCreatedBy(review.getReviewCreatedBy())
                .reviewLikes(review.getReviewLikes())
                .userAvatarUrl(review.getUser() == null ? null : review.getUser().getUserAvatarUrl())
                .userFullName(review.getUser() == null ? null : review.getUser().getUserFullName())
                .build();
    }
}
