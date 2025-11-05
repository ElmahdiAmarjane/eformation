package com.example.eformation.services;

import com.example.eformation.dtos.PlayList.PlaylistRequest;
import com.example.eformation.dtos.PlayList.PlaylistResponse;
import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.models.user.Professeur;
import com.example.eformation.repository.PlaylistRepository;
import com.example.eformation.repository.ProfesseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final ProfesseurRepository professeurRepository;

    public PlaylistService(PlaylistRepository playlistRepository, ProfesseurRepository professeurRepository) {
        this.playlistRepository = playlistRepository;
        this.professeurRepository = professeurRepository;
    }

    public PlaylistResponse createPlaylist(PlaylistRequest request) {
        Professeur prof = professeurRepository.findById(request.getProfId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        PlayList playlist = new PlayList();
        playlist.setTitle(request.getTitle());
        playlist.setDescription(request.getDescription());
        playlist.setVisibility(request.getVisibility());
        playlist.setMiniature(request.getMiniature());
        playlist.setProfesseur(prof);

        PlayList saved = playlistRepository.save(playlist);

        return new PlaylistResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getVisibility(),
                saved.getMiniature(),
                saved.getDateCreation(),
                prof
        );
    }

    public List<PlaylistResponse> getAllPlaylists() {
        return playlistRepository.findAll().stream()
                .map(p -> new PlaylistResponse(
                        p.getId(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getVisibility(),
                        p.getMiniature(),
                        p.getDateCreation(),
                        p.getProfesseur()
                ))
                .collect(Collectors.toList());
    }

    public PlaylistResponse getPlaylistById(Long id) {
        PlayList p = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        return new PlaylistResponse(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getVisibility(),
                p.getMiniature(),
                p.getDateCreation(),
                p.getProfesseur()
        );
    }

    // ✅ Get all playlists by professor
    public List<PlaylistResponse> getPlaylistsByProfId(Long profId) {
        Professeur professeur = professeurRepository.findById(profId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        return playlistRepository.findByProfesseur(professeur).stream()
                .map(p -> new PlaylistResponse(
                        p.getId(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getVisibility(),
                        p.getMiniature(),
                        p.getDateCreation(),
                        p.getProfesseur()
                ))
                .collect(Collectors.toList());
    }

    // ✅ Update playlist by ID
    public PlaylistResponse updatePlaylist(Long id, PlaylistRequest request) {
        PlayList existing = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        // Update only the provided fields
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setVisibility(request.getVisibility());
        existing.setMiniature(request.getMiniature());

        PlayList updated = playlistRepository.save(existing);

        return new PlaylistResponse(
                updated.getId(),
                updated.getTitle(),
                updated.getDescription(),
                updated.getVisibility(),
                updated.getMiniature(),
                updated.getDateCreation(),
                updated.getProfesseur()
        );
    }

    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}
