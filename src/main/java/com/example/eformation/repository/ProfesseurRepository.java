package com.example.eformation.repository;

import com.example.eformation.models.user.Professeur;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    
    Optional<Professeur> findByEmail(String email);

    boolean existsByEmail(String email);
}
