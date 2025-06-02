package org.example.foodee_service.repository;

import org.example.foodee_service.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    boolean existsUserByUserUsername(String username);
    boolean existsUserByUserEmail(String email);
    boolean existsUserByUserPhone(String phone);

    Optional<User> findByUserUsername(String username);

    @EntityGraph(attributePaths = "roles")
    Page<User> findAll(Pageable pageable);

    Page<User> getUsersByUserStatusIsNot(String userStatus, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.userUsername = :username AND u.userStatus <> 'DELETED'")
    Optional<User> findByUsernameAndNotDeleted(String username);

    @Query("SELECT u FROM User u WHERE u.userStatus <> 'DELETED' AND u.userId = :userId")
    Optional<User> findByUserId(String userId);

    @Query("select u from User u where u.userId =:id")
    User findUserByUserId(String id);

    Optional<User> findByUserEmail(String userEmail);
}
