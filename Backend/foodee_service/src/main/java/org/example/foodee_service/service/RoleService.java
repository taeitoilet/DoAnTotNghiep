package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.role_request.RoleCreateRequest;
import org.example.foodee_service.dto.request.role_request.RolePagingQueryRequest;
import org.example.foodee_service.dto.request.role_request.RoleQueryRequest;
import org.example.foodee_service.dto.request.role_request.RoleUpdateRequest;
import org.example.foodee_service.dto.response.roleResponse.RoleResponseDto;

import java.util.List;

public interface RoleService {
    RoleResponseDto createRole(RoleCreateRequest request);
    
    RoleResponseDto getRoleByName(String name);
    
    List<RoleResponseDto> getRolesPaging(RolePagingQueryRequest request, long[] total);
    
    List<RoleResponseDto> getRoles(RoleQueryRequest request);
    
    RoleResponseDto updateRole(String name, RoleUpdateRequest request);
    
    void deleteRole(String name);
} 