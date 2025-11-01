package com.example.eformation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eformation.models.playlist.*;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<PlayList, Long> {
    List<PlayList> findByProfesseurId(Long profId);
}
