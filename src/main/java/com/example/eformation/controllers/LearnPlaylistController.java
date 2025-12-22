package com.example.eformation.controllers;

import com.example.eformation.dtos.LearnPlaylist.*;
import com.example.eformation.services.LearnPlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/learn-playlist")
@CrossOrigin
public class LearnPlaylistController {

    private final LearnPlaylistService learnPlaylistService;

    public LearnPlaylistController(LearnPlaylistService learnPlaylistService) {
        this.learnPlaylistService = learnPlaylistService;
    }

    // =====================================================
    // STUDENT REQUEST ACCESS
    // =====================================================
    @PostMapping("/request-access")
    public LearnPlaylistResponse requestAccess(@RequestBody Map<String, Long> body) {
        return learnPlaylistService.requestAccess(
                body.get("studentId"),
                body.get("playlistId")
        );
    }

    // =====================================================
    // PROFESSOR - STUDENTS
    // =====================================================
    @GetMapping("/prof/{profId}/students")
    public List<LearnPlaylistResponse> getAll(@PathVariable Long profId) {
        return learnPlaylistService.getAllStudentsOfProfessor(profId);
    }

    @GetMapping("/prof/{profId}/students/pending")
    public List<LearnPlaylistResponse> getPending(@PathVariable Long profId) {
        return learnPlaylistService.getPendingStudentsOfProfessor(profId);
    }

    @GetMapping("/prof/{profId}/students/verified")
    public List<LearnPlaylistResponse> getVerified(@PathVariable Long profId) {
        return learnPlaylistService.getVerifiedStudentsOfProfessor(profId);
    }

    // =====================================================
    // VERIFY / UPDATE
    // =====================================================
    @PutMapping("/{learnPlaylistId}/verify")
    public LearnPlaylistResponse verify(
            @PathVariable Long learnPlaylistId,
            @RequestBody UpdateVerificationRequest request
    ) {
        return learnPlaylistService.updateVerification(
                learnPlaylistId,
                request.isVerified()
        );
    }

    // =====================================================
    // DELETE
    // =====================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        learnPlaylistService.deleteLearnPlaylist(id);
        return ResponseEntity.ok("Student removed from playlist successfully");
    }

    // =====================================================
    // INVITE
    // =====================================================
    @PostMapping("/invite")
    public ResponseEntity<String> invite(@RequestBody SendInvitationRequest request) {
        learnPlaylistService.sendInvitation(request);
        return ResponseEntity.ok(
                "Invitation sent to " + request.getStudentEmail()
        );
    }
}
