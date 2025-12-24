package com.example.eformation.controllers;

import com.example.eformation.dtos.LearnPlaylist.*;
import com.example.eformation.services.LearnPlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/learn-playlist")
public class LearnPlaylistController {

    private final LearnPlaylistService learnPlaylistService;

    public LearnPlaylistController(LearnPlaylistService learnPlaylistService) {
        this.learnPlaylistService = learnPlaylistService;
    }

    // =====================================================
    // INVITE STUDENT TO PLAYLIST
    // =====================================================
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
}
