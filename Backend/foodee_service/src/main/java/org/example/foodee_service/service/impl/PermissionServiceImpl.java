package org.example.foodee_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.permission_request.PermissionCreateRequest;
import org.example.foodee_service.dto.request.permission_request.PermissionPagingQueryRequest;
import org.example.foodee_service.dto.request.permission_request.PermissionUpdateRequest;
import org.example.foodee_service.dto.response.permissionResponse.PermissionResponseDto;
import org.example.foodee_service.entity.Permission;
import org.example.foodee_service.exception.ResourceAlreadyExistsException;
import org.example.foodee_service.exception.ResourceNotFoundException;
import org.example.foodee_service.repository.PermissionRepository;
import org.example.foodee_service.service.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponseDto createPermission(PermissionCreateRequest request) {
        // Check if permission already exists
        if (permissionRepository.existsByPermName(request.getName())) {
            throw new ResourceAlreadyExistsException("Permission", "name", request.getName());
        }

        // Create new permission entity
        Permission permission = Permission.builder()
                .permName(request.getName())
                .permDescription(request.getDescription())
                .permCreatedAt(LocalDateTime.now())
                .permCreatedBy("system") // In a real application, this would be from authenticated user
                .permUpdatedAt(LocalDateTime.now())
                .permUpdatedBy("system") // In a real application, this would be from authenticated user
                .build();

        // Save permission to database
        Permission savedPermission = permissionRepository.save(permission);

        // Return response DTO
        return mapToResponseDto(savedPermission);
    }

    @Override
    public PermissionResponseDto getPermissionByName(String name) {
        Permission permission = permissionRepository.findByPermName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", name));

        return mapToResponseDto(permission);
    }

    @Override
    public List<PermissionResponseDto> getPermissionsPaging(PermissionPagingQueryRequest request, long[] total) {
        // Create specification based on filters
        Specification<Permission> spec = createSpecification(request);
        
        // Create pageable object from request
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );

        // Use specification for filtering
        Page<Permission> permissionsPage;
        if (spec != null) {
            permissionsPage = permissionRepository.findAll(spec, pageable);
        } else {
            permissionsPage = permissionRepository.findAll(pageable);
        }

        // Set total count if requested
        if (request.getPage().getGetTotal()) {
            total[0] = permissionsPage.getTotalElements();
        }

        return permissionsPage.getContent().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public PermissionResponseDto updatePermission(String name, PermissionUpdateRequest request) {
        // Check if permission exists
        Permission permission = permissionRepository.findByPermName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", name));

        // Update fields
        permission.setPermDescription(request.getDescription());
        permission.setPermUpdatedAt(LocalDateTime.now());
        permission.setPermUpdatedBy("system"); // In a real application, this would be from authenticated user

        // Save updated permission
        Permission updatedPermission = permissionRepository.save(permission);

        // Return response DTO
        return mapToResponseDto(updatedPermission);
    }

    @Override
    public void deletePermission(String name) {
        // Check if permission exists
        Permission permission = permissionRepository.findByPermName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", name));

        // Delete permission
        permissionRepository.delete(permission);
    }

    // Helper method to map Permission entity to PermissionResponseDto
    private PermissionResponseDto mapToResponseDto(Permission permission) {
        return PermissionResponseDto.builder()
                .name(permission.getPermName())
                .description(permission.getPermDescription())
                .createdAt(permission.getPermCreatedAt())
                .createdBy(permission.getPermCreatedBy())
                .updatedAt(permission.getPermUpdatedAt())
                .updatedBy(permission.getPermUpdatedBy())
                .build();
    }
    
    private Specification<Permission> createSpecification(PermissionPagingQueryRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Add direct filter predicates if provided
            if (request.getName() != null && !request.getName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }
            
            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + request.getDescription().toLowerCase() + "%"));
            }
            
            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
} 