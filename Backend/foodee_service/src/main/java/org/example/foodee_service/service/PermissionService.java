package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.permission_request.PermissionCreateRequest;
import org.example.foodee_service.dto.request.permission_request.PermissionPagingQueryRequest;
import org.example.foodee_service.dto.request.permission_request.PermissionUpdateRequest;
import org.example.foodee_service.dto.response.permissionResponse.PermissionResponseDto;

import java.util.List;

public interface PermissionService {
    PermissionResponseDto createPermission(PermissionCreateRequest request);
    
    PermissionResponseDto getPermissionByName(String name);
    
    List<PermissionResponseDto> getPermissionsPaging(PermissionPagingQueryRequest request, long[] total);
    
    PermissionResponseDto updatePermission(String name, PermissionUpdateRequest request);
    
    void deletePermission(String name);
} 