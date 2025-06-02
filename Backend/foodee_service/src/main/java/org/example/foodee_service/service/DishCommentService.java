package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.dishCommentRequest.CreateDishCommentRequest;
import org.example.foodee_service.dto.request.dishCommentRequest.DeleteDishCommentRequest;
import org.example.foodee_service.dto.request.dishCommentRequest.GetCommentByDishId;
import org.example.foodee_service.dto.request.dishCommentRequest.UpdateDishCommentRequest;
import org.example.foodee_service.dto.response.comment.DishCommentResponseDto;


import java.util.List;

public interface DishCommentService {
    List<DishCommentResponseDto> getAllComment(GetCommentByDishId request, long[] total);
    DishCommentResponseDto createComment(CreateDishCommentRequest request);
    DishCommentResponseDto updateComment(UpdateDishCommentRequest request);
    void deleteComment(DeleteDishCommentRequest request);
}
