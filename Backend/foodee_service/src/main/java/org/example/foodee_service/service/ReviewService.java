package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.reviewRequest.CreateReviewRequest;
import org.example.foodee_service.dto.request.reviewRequest.DeleteReviewRequest;
import org.example.foodee_service.dto.request.reviewRequest.GetReviewById;
import org.example.foodee_service.dto.request.reviewRequest.UpdateReviewRequest;
import org.example.foodee_service.dto.response.review.ReviewResponseDto;

import java.util.List;

public interface ReviewService {
    List<ReviewResponseDto> getAllReview(GetReviewById request, long[] total);
    ReviewResponseDto createReview(CreateReviewRequest request);
    ReviewResponseDto updateReview(UpdateReviewRequest request);
    void deleteReview(DeleteReviewRequest request);
}
