package org.example.foodee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.dishCommentRequest.CreateDishCommentRequest;
import org.example.foodee_service.dto.request.dishCommentRequest.DeleteDishCommentRequest;
import org.example.foodee_service.dto.request.dishCommentRequest.GetCommentByDishId;
import org.example.foodee_service.dto.request.dishCommentRequest.UpdateDishCommentRequest;
import org.example.foodee_service.dto.response.comment.DishCommentResponseDto;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.service.DishCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Validated
public class DishCommentController {
    private final DishCommentService dishCommentService;

    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<DishCommentResponseDto>> createComment(@RequestBody @Valid CreateDishCommentRequest request){
        DishCommentResponseDto dishCommnent = dishCommentService.createComment(request);
        ResultObject<DishCommentResponseDto> response = ResultObject.success(dishCommnent);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/update/v1.0")
    public ResponseEntity<ResultObject<DishCommentResponseDto>> updateComment(@RequestBody @Valid UpdateDishCommentRequest request){
        DishCommentResponseDto dishCommnent = dishCommentService.updateComment(request);
        ResultObject<DishCommentResponseDto> response = ResultObject.success(dishCommnent);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<DishCommentResponseDto>> listComment( @RequestBody @Valid GetCommentByDishId request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];
        List<DishCommentResponseDto> dishCommnents = dishCommentService.getAllComment(request,total);
        ResultList<DishCommentResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(dishCommnents, total[0]);
        } else {
            response = ResultList.success(dishCommnents);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<String>> deleteDish(@RequestBody @Valid DeleteDishCommentRequest request){
        dishCommentService.deleteComment(request);
        ResultObject<String> response = ResultObject.success("Xóa thành công !");
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
}
