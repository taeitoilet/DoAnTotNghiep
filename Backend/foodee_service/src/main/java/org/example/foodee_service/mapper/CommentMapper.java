package org.example.foodee_service.mapper;

import org.example.foodee_service.dto.response.comment.DishCommentResponseDto;
import org.example.foodee_service.entity.DishCommnent;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public DishCommentResponseDto toDishCommentResponseDto(DishCommnent dishCommnent){
        return DishCommentResponseDto.builder()
                .commentContent(dishCommnent.getCommentContent())
                .commentId(dishCommnent.getCommentId())
                .commentCreateAt(dishCommnent.getCommentCreateAt())
                .commentCreateBy(dishCommnent.getCommentCreateBy())
                .commentRating(dishCommnent.getCommentRating())
                .userAvatarUrl(dishCommnent.getUser() == null ? null : dishCommnent.getUser().getUserAvatarUrl())
                .userFullName(dishCommnent.getUser() == null ? null : dishCommnent.getUser().getUserFullName())
                .dishId(dishCommnent.getDish().getDishId())
                .build();
    }
}
