package com.example.eformation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eformation.models.Packs.Pack;
import com.example.eformation.models.Packs.PackType;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long>{

    boolean existsByType(PackType type);
}
