package org.example.foodee_service.dto.response.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodee_service.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDto {

    private Long reviewId;

    private String reviewComment;

    private BigDecimal reviewAvgRating;

    private Integer reviewLikes;

    private LocalDateTime reviewCreatedAt;

    private String reviewCreatedBy;

    private String userFullName;
    private String userAvatarUrl;
}
