package com.example.eformation.controllers;

import com.example.eformation.dtos.LearnPlaylist.LearnPlaylistStudentResponse;
import com.example.eformation.dtos.LearnPlaylist.SendInvitationRequest;
import com.example.eformation.models.LearnPlaylist;
import com.example.eformation.services.LearnPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/learn-playlist")
@RequiredArgsConstructor
public class LearnPlaylistController {

    private final LearnPlaylistService learnPlaylistService;

    @PostMapping("/invite")
    public ResponseEntity<String> invite(@RequestBody SendInvitationRequest request) {
        learnPlaylistService.sendInvitation(request);
        return ResponseEntity.ok("Invitation sent to " + request.getStudentEmail());
    }

    @GetMapping("/prof/{profId}/students")
    public ResponseEntity<List<LearnPlaylistStudentResponse>> getStudentsOfProfessor(
            @PathVariable Long profId
    ) {
        return ResponseEntity.ok(
                learnPlaylistService.getStudentsByProfessor(profId)
        );
    }

    @PatchMapping("/{learnPlaylistId}/verify")
    public ResponseEntity<String> verifyStudent(
            @PathVariable Long learnPlaylistId,
            @RequestBody Map<String, Boolean> request
    ) {
        boolean verified = request.getOrDefault("verified", false);
        learnPlaylistService.verifyStudentInPlaylist(learnPlaylistId, verified);
        return ResponseEntity.ok("Student verification updated to: " + verified);
    }

    // ===============================
    // New API: Get all playlists of a student
    // ===============================
    @GetMapping("/student/{studentId}/playlists")
    public ResponseEntity<List<LearnPlaylistStudentResponse>> getPlaylistsOfStudent(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(
                learnPlaylistService.getPlaylistsOfStudent(studentId)
        );
    }
}
