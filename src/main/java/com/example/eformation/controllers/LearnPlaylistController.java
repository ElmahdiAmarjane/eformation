package com.example.eformation.controllers;

import com.example.eformation.dtos.LearnPlaylist.LearnPlaylistRequest;
import com.example.eformation.dtos.LearnPlaylist.LearnPlaylistResponse;
import com.example.eformation.services.LearnPlaylistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learn-playlist")
public class LearnPlaylistController {

    private final LearnPlaylistService learnPlaylistService;

    public LearnPlaylistController(LearnPlaylistService learnPlaylistService) {
        this.learnPlaylistService = learnPlaylistService;
    }

    // ----------------------------
    // 1. Student requests access to a playlist
    // ----------------------------
    @PostMapping("/request-access")
    public LearnPlaylistResponse requestAccess(@RequestBody LearnPlaylistRequest request) {
        return learnPlaylistService.requestAccess(request);
    }

    // ----------------------------
    // 2. Professor fetches all students linked to him
    // ----------------------------
    @GetMapping("/prof/{profId}/students")
    public List<LearnPlaylistResponse> getAllStudents(@PathVariable Long profId) {
        return learnPlaylistService.getAllStudentsOfProfessor(profId);
    }

    // ----------------------------
    // 3. Professor fetches students who requested access (pending)
    // ----------------------------
    @GetMapping("/prof/{profId}/students/pending")
    public List<LearnPlaylistResponse> getPendingStudents(@PathVariable Long profId) {
        return learnPlaylistService.getPendingStudentsOfProfessor(profId);
    }

    // ----------------------------
    // 4. Professor fetches verified students
    // ----------------------------
    @GetMapping("/prof/{profId}/students/verified")
    public List<LearnPlaylistResponse> getVerifiedStudents(@PathVariable Long profId) {
        return learnPlaylistService.getVerifiedStudentsOfProfessor(profId);
    }

    // ----------------------------
    // 5. Professor verifies a student
    // ----------------------------
    @PutMapping("/verify/{learnPlaylistId}")
    public LearnPlaylistResponse verifyStudent(@PathVariable Long learnPlaylistId) {
        return learnPlaylistService.verifyStudent(learnPlaylistId);
    }
}
