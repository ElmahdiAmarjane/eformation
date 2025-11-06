package com.example.eformation.repository;

import com.example.eformation.models.playlist.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChapitreRepository extends JpaRepository<Chapitre, Long> {
    List<Chapitre> findByPlaylistId(Long playlistId);
}
