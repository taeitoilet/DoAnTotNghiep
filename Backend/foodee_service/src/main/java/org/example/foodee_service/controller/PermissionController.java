package org.example.foodee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.common.DetailQueryRequest;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.permission_request.PermissionCreateRequest;
import org.example.foodee_service.dto.request.permission_request.PermissionPagingQueryRequest;
import org.example.foodee_service.dto.request.permission_request.PermissionUpdateRequest;
import org.example.foodee_service.dto.response.permissionResponse.PermissionResponseDto;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authorization/auth/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<PermissionResponseDto>> createPermission(
            @Valid @RequestBody PermissionCreateRequest request) {
        PermissionResponseDto createdPermission = permissionService.createPermission(request);
        ResultObject<PermissionResponseDto> response = ResultObject.success(createdPermission);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/detail/v1.0")
    public ResponseEntity<ResultObject<PermissionResponseDto>> getPermissionDetail(
            @RequestBody DetailQueryRequest request) {
        PermissionResponseDto permission = permissionService.getPermissionByName(request.getId());
        ResultObject<PermissionResponseDto> response = ResultObject.success(permission);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<PermissionResponseDto>> getPermissionList(
            @RequestBody PermissionPagingQueryRequest request) {
        // Check default values for paging
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        
        long[] total = new long[1];
        List<PermissionResponseDto> permissions = permissionService.getPermissionsPaging(request, total);
        
        ResultList<PermissionResponseDto> response;
        if (request.getPage().getGetTotal() != null && request.getPage().getGetTotal()) {
            response = ResultList.success(permissions, total[0]);
        } else {
            response = ResultList.success(permissions);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/v1.0")
    public ResponseEntity<ResultObject<PermissionResponseDto>> updatePermission(
            @RequestParam String name,
            @Valid @RequestBody PermissionUpdateRequest request) {
        PermissionResponseDto updatedPermission = permissionService.updatePermission(name, request);
        ResultObject<PermissionResponseDto> response = ResultObject.success(updatedPermission);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete/v1.0")
    public ResponseEntity<ResultObject<Void>> deletePermission(
            @RequestParam String name) {
        permissionService.deletePermission(name);
        ResultObject<Void> response = ResultObject.success(null);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
} 