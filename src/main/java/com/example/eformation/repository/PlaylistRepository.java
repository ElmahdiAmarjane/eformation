package com.example.eformation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.models.user.Professeur;
import java.util.List;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {

    // Find playlists by professor ID - use 'professeur' because that's the field name in PlayList entity
    List<PlayList> findByProfesseurId(Long profId);

    // Find playlists by Professeur entity
    List<PlayList> findByProfesseur(Professeur professeur);
}