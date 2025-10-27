package com.example.eformation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.eformation.models.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
