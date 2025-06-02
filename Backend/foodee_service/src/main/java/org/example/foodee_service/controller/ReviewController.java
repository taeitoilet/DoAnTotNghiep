package org.example.foodee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.reviewRequest.CreateReviewRequest;
import org.example.foodee_service.dto.request.reviewRequest.DeleteReviewRequest;
import org.example.foodee_service.dto.request.reviewRequest.GetReviewById;
import org.example.foodee_service.dto.request.reviewRequest.UpdateReviewRequest;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.dto.response.review.ReviewResponseDto;
import org.example.foodee_service.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Validated
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<ReviewResponseDto>> createReview(@RequestBody @Valid CreateReviewRequest request){
        ReviewResponseDto review = reviewService.createReview(request);
        ResultObject<ReviewResponseDto> response = ResultObject.success(review);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/v1.0")
    public ResponseEntity<ResultObject<ReviewResponseDto>> updateReview(@RequestBody @Valid UpdateReviewRequest request){
        ReviewResponseDto review = reviewService.updateReview(request);
        ResultObject<ReviewResponseDto> response = ResultObject.success(review);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<ReviewResponseDto>> listReview(GetReviewById request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];
        List<ReviewResponseDto> reviews = reviewService.getAllReview(request,total);
        ResultList<ReviewResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(reviews, total[0]);
        } else {
            response = ResultList.success(reviews);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<String>> deleteDish(@RequestBody @Valid DeleteReviewRequest request){
        reviewService.deleteReview(request);
        ResultObject<String> response = ResultObject.success("Xóa thành công !");
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
}
