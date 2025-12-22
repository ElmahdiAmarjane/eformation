package com.example.eformation.services;

import com.example.eformation.dtos.PlayList.PlaylistRequest;
import com.example.eformation.dtos.PlayList.PlaylistResponse;
import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.models.user.Professeur;
import com.example.eformation.repository.PlayListRepository;
import com.example.eformation.repository.ProfesseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final PlayListRepository playlistRepository;
    private final ProfesseurRepository professeurRepository;

    public PlaylistService(
            PlayListRepository playlistRepository,
            ProfesseurRepository professeurRepository
    ) {
        this.playlistRepository = playlistRepository;
        this.professeurRepository = professeurRepository;
    }

    // =====================================================
    // CREATE
    // =====================================================
    public PlaylistResponse createPlaylist(PlaylistRequest request) {

        Professeur prof = professeurRepository.findById(request.getProfId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        PlayList playlist = new PlayList();
        playlist.setTitle(request.getTitle());
        playlist.setDescription(request.getDescription());
        playlist.setVisibility(request.getVisibility());
        playlist.setMiniature(request.getMiniature());
        playlist.setProfesseur(prof); // ✅ FIX

        PlayList saved = playlistRepository.save(playlist);

        return mapToResponse(saved);
    }

    // =====================================================
    // READ
    // =====================================================
    public List<PlaylistResponse> getAllPlaylists() {
        return playlistRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PlaylistResponse getPlaylistById(Long id) {
        PlayList playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        return mapToResponse(playlist);
    }

    // =====================================================
    // READ BY PROFESSOR
    // =====================================================
    public List<PlaylistResponse> getPlaylistsByProfId(Long profId) {
        Professeur professor = professeurRepository.findById(profId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));
    
        return playlistRepository.findByProfesseur(professor)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =====================================================
    // UPDATE
    // =====================================================
    public PlaylistResponse updatePlaylist(Long id, PlaylistRequest request) {

        PlayList existing = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setVisibility(request.getVisibility());
        existing.setMiniature(request.getMiniature());

        PlayList updated = playlistRepository.save(existing);
        return mapToResponse(updated);
    }

    // =====================================================
    // DELETE
    // =====================================================
    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

    // =====================================================
    // MAPPER
    // =====================================================
    private PlaylistResponse mapToResponse(PlayList p) {
        return new PlaylistResponse(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getVisibility(),
                p.getMiniature(),
                p.getDateCreation(),
                p.getProfesseur() // ✅ FIX
        );
    }
}
