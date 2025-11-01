package com.example.eformation.services;

import com.example.eformation.dtos.PlayList.PlaylistRequest;
import com.example.eformation.dtos.PlayList.PlaylistResponse;
import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.models.user.Professeur;
import com.example.eformation.repository.PlaylistRepository;
import com.example.eformation.repository.ProfesseurRepository;
import com.example.eformation.repository.PackRepository;

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
        playlist.setProfesseur(prof);

        PlayList saved = playlistRepository.save(playlist);

        return new PlaylistResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                prof
        );
    }

    public List<PlaylistResponse> getAllPlaylists() {
        return playlistRepository.findAll().stream()
                .map(p -> new PlaylistResponse(
                        p.getId(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getProfesseur() != null ? p.getProfesseur() : null
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
                p.getProfesseur() != null ? p.getProfesseur(): null
        );
    }

    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}
