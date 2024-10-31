package com.ClaimsManagement.SecurityMicroservice.Repository;

import com.ClaimsManagement.SecurityMicroservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value="SELECT policy_id FROM users WHERE username = :userName", nativeQuery = true)
    String findPolicyIdByUsername(@Param("userName") String userName);

    @Query(value="SELECT role FROM users WHERE username = :userName", nativeQuery = true)
    String findRoleByUsername(@Param("userName") String userName);
}
