package org.example.foodee_service.repository;

import org.example.foodee_service.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
    boolean existsByRoleName(String name);

//    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions WHERE r.name = :roleName")
//    @Transactional
//    Role getRoleByRole_nameWithPermissions(@Param("roleName") String roleName);

    @EntityGraph(attributePaths = "permissions")
    Page<Role> findAll(Pageable pageable);

    Optional<Role> getByRoleName(String name);

    Set<Role> getRoleByRoleName(String roleName);
}