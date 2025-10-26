package com.example.eformation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.eformation.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
