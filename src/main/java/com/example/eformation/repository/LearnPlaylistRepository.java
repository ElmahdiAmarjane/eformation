package com.example.eformation.repository;


import com.example.eformation.models.LearnPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearnPlaylistRepository extends JpaRepository<LearnPlaylist, Long> {

    // Find all LearnPlaylist by student id
    List<LearnPlaylist> findByEtudiantId(Long studentId);

    // Find all LearnPlaylist by professor id
    List<LearnPlaylist> findByProfessor_Id(Long profId);

    // Find all LearnPlaylist by playlist id
    List<LearnPlaylist> findByPlaylistId(Long playlistId);

    // Find students who requested access (verified = false)
    List<LearnPlaylist> findByProfessor_IdAndVerifiedFalse(Long profId);

    // Find students who are verified (verified = true)
    List<LearnPlaylist> findByProfessor_IdAndVerifiedTrue(Long profId);

    // Find all LearnPlaylist by professor id and verified status
    List<LearnPlaylist> findByProfessor_IdAndVerified(Long profId, boolean verified);
}
