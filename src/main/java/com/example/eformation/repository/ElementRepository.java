package com.example.eformation.repository;

import com.example.eformation.models.playlist.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {

    // Fetch all elements for a specific chapitre
    List<Element> findByChapitreId(Long chapitreId);
}

