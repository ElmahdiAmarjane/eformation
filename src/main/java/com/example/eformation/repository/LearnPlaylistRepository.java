package com.example.eformation.repository;

import com.example.eformation.models.LearnPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearnPlaylistRepository extends JpaRepository<LearnPlaylist, Long> {

    // By student
    List<LearnPlaylist> findByEtudiantId(Long studentId);

    // By professor - CORRECTED: use 'professor' not 'professeur'
    List<LearnPlaylist> findByProfessorId(Long professorId);

    // By professor with verified status
    List<LearnPlaylist> findByProfessorIdAndVerifiedTrue(Long professorId);
    List<LearnPlaylist> findByProfessorIdAndVerifiedFalse(Long professorId);

    // By playlist
    List<LearnPlaylist> findByPlaylistId(Long playlistId);

    // Generic query
    List<LearnPlaylist> findByProfessorIdAndVerified(Long professorId, boolean verified);

    // Check if student already has access
    boolean existsByEtudiantIdAndPlaylistId(Long etudiantId, Long playlistId);

    // Find specific relationship
    Optional<LearnPlaylist> findByEtudiantIdAndPlaylistId(Long etudiantId, Long playlistId);
    
    // Additional query if needed
    Optional<LearnPlaylist> findByEtudiantIdAndPlaylistIdAndProfessorId(Long etudiantId, Long playlistId, Long professorId);

}