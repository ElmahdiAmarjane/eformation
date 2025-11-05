package com.example.eformation.controllers;

import com.example.eformation.dtos.PlayList.PlaylistRequest;
import com.example.eformation.dtos.PlayList.PlaylistResponse;
import com.example.eformation.services.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/create")
    public ResponseEntity<PlaylistResponse> createPlaylist(@RequestBody PlaylistRequest request) {
        return ResponseEntity.ok(playlistService.createPlaylist(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlaylistResponse>> getAllPlaylists() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> getPlaylistById(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.getPlaylistById(id));
    }

    @GetMapping("/by-prof/{profId}")
    public ResponseEntity<List<PlaylistResponse>> getPlaylistsByProfId(@PathVariable Long profId) {
        return ResponseEntity.ok(playlistService.getPlaylistsByProfId(profId));
    }

    // ✅ Update playlist by ID
    @PutMapping("update/{id}")
    public ResponseEntity<PlaylistResponse> updatePlaylist(
            @PathVariable Long id,
            @RequestBody PlaylistRequest request
    ) {
        return ResponseEntity.ok(playlistService.updatePlaylist(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.ok("Playlist deleted successfully");
    }
}
