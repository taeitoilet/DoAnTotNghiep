package org.example.foodee_service.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishCommentResponseDto {
    private Long commentId;
    private String commentContent;
    private BigDecimal commentRating;
    private LocalDateTime commentCreateAt;
    private String commentCreateBy;
    private String userFullName;
    private String userAvatarUrl;
    private Long dishId;
}
