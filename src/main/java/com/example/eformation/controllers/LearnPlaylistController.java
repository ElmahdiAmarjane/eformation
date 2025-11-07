package com.example.eformation.controllers;

import com.example.eformation.dtos.LearnPlaylist.*;
import com.example.eformation.models.LearnPlaylist;
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

    // Student requests access using simple JSON with IDs
    @PostMapping("/request-access")
    public LearnPlaylistResponse requestAccess(@RequestBody Map<String, Long> body) {
        Long studentId = body.get("studentId") != null ? body.get("studentId") : body.get("etudiantId");
        Long playlistId = body.get("playlistId") != null ? body.get("playlistId") : body.get("playlistId");
        return learnPlaylistService.requestAccess(studentId, playlistId);
    }

    // Fetch all students (rich)
    @GetMapping("/prof/{profId}/students")
    public List<LearnPlaylistResponse> getAllStudents(@PathVariable Long profId) {
        return learnPlaylistService.getAllStudentsOfProfessor(profId);
    }

    // Fetch pending (rich)
    @GetMapping("/prof/{profId}/students/pending")
    public List<LearnPlaylistResponse> getPending(@PathVariable Long profId) {
        return learnPlaylistService.getPendingStudentsOfProfessor(profId);
    }

    // Fetch verified (rich)
    @GetMapping("/prof/{profId}/students/verified")
    public List<LearnPlaylistResponse> getVerified(@PathVariable Long profId) {
        return learnPlaylistService.getVerifiedStudentsOfProfessor(profId);
    }

    // Verify (returns rich)
    @PutMapping("/verify/{learnPlaylistId}")
    public LearnPlaylistResponse verify(@PathVariable Long learnPlaylistId) {
        return learnPlaylistService.verifyStudent(learnPlaylistId);
    }

        // Update student access status (verified or pending)
        @PutMapping("/{learnPlaylistId}/verify")
        public LearnPlaylistResponse verifyStudent(
                @PathVariable Long learnPlaylistId,
                @RequestBody UpdateVerificationRequest request
        ) {
            return learnPlaylistService.updateVerification(learnPlaylistId, request.isVerified());
        }

    // Delete student from playlist
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLearnPlaylist(@PathVariable Long id) {
        learnPlaylistService.deleteLearnPlaylist(id);
        return ResponseEntity.ok("Student removed from playlist successfully");
    }

    //send email
@PostMapping("/invite")
public ResponseEntity<String> sendInvitation(@RequestBody SendInvitationRequest request) {
    learnPlaylistService.sendInvitation(request);
    return ResponseEntity.ok("Invitation sent successfully to " + request.getStudentEmail());
}
}
