package com.example.eformation.controllers;

import com.example.eformation.dtos.chapitre.ChapitreRequest;
import com.example.eformation.dtos.chapitre.ChapitreResponse;
import com.example.eformation.services.ChapitreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chapitres")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChapitreController {

    private final ChapitreService chapitreService;

    // Create new chapter
    @PostMapping("/create")
    public ResponseEntity<ChapitreResponse> create(@RequestBody ChapitreRequest request) {
        ChapitreResponse response = chapitreService.createChapitre(request);
        return ResponseEntity.ok(response);
    }

    //  Get chapters by playlist
    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<List<ChapitreResponse>> getByPlaylist(@PathVariable Long playlistId) {
        List<ChapitreResponse> chapters = chapitreService.getChapitresByPlaylist(playlistId);
        return ResponseEntity.ok(chapters);
    }

    // Update Chapitre
    @PutMapping("update/{id}")
    public ResponseEntity<ChapitreResponse> update(@PathVariable Long id, @RequestBody ChapitreRequest request) {
        ChapitreResponse updated = chapitreService.updateChapitre(id, request);
        return ResponseEntity.ok(updated);
    }

    // Delete chapter
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String message = chapitreService.deleteChapitre(id);
        return ResponseEntity.ok(message);
    }
}
