package com.example.eformation.services;

import com.example.eformation.dtos.chapitre.ChapitreRequest;
import com.example.eformation.dtos.chapitre.ChapitreResponse;
import com.example.eformation.models.playlist.Chapitre;
import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.repository.ChapitreRepository;
import com.example.eformation.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChapitreService {

    private final ChapitreRepository chapitreRepository;
    private final PlaylistRepository playListRepository;

    // ✅ Create a new chapter
    public ChapitreResponse createChapitre(ChapitreRequest request) {
        PlayList playlist = playListRepository.findById(request.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Chapitre chapitre = new Chapitre();
        chapitre.setTitre(request.getTitre());
        chapitre.setPlaylist(playlist);

        Chapitre savedChapitre = chapitreRepository.save(chapitre);

        return new ChapitreResponse(
                savedChapitre.getId(),
                savedChapitre.getTitre(),
                savedChapitre.getDatePublication(),
                savedChapitre.getPlaylist().getId()
        );
    }

    // ✅ Get all chapters by playlist
    public List<ChapitreResponse> getChapitresByPlaylist(Long playlistId) {
        return chapitreRepository.findByPlaylistId(playlistId)
                .stream()
                .map(c -> new ChapitreResponse(
                        c.getId(),
                        c.getTitre(),
                        c.getDatePublication(),
                        c.getPlaylist().getId()
                ))
                .collect(Collectors.toList());
    }

    // ✅ Update chapter
    public ChapitreResponse updateChapitre(Long id, ChapitreRequest request) {
        Chapitre chapitre = chapitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapitre not found"));

        chapitre.setTitre(request.getTitre());
        Chapitre updatedChapitre = chapitreRepository.save(chapitre);

        return new ChapitreResponse(
                updatedChapitre.getId(),
                updatedChapitre.getTitre(),
                updatedChapitre.getDatePublication(),
                updatedChapitre.getPlaylist().getId()
        );
    }

    // ✅ Delete chapter
    public String deleteChapitre(Long id) {
        Chapitre chapitre = chapitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapitre not found"));
        chapitreRepository.delete(chapitre);
        return "Chapter deleted successfully";
    }
}
