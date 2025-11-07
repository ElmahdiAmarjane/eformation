package com.example.eformation.services;

import com.example.eformation.dtos.LearnPlaylist.LearnPlaylistRequest;
import com.example.eformation.dtos.LearnPlaylist.LearnPlaylistResponse;
import com.example.eformation.models.LearnPlaylist;
import com.example.eformation.models.user.Etudiant;
import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.models.user.Professeur;
import com.example.eformation.repository.EtudiantRepository;
import com.example.eformation.repository.LearnPlaylistRepository;
import com.example.eformation.repository.PlayListRepository;
import com.example.eformation.repository.ProfesseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LearnPlaylistService {

    private final LearnPlaylistRepository repository;
    private final EtudiantRepository etudiantRepository;
    private final PlayListRepository playListRepository;
    private final ProfesseurRepository professeurRepository;

    public LearnPlaylistService(LearnPlaylistRepository repository,
                                EtudiantRepository etudiantRepository,
                                PlayListRepository playListRepository,
                                ProfesseurRepository professeurRepository) {
        this.repository = repository;
        this.etudiantRepository = etudiantRepository;
        this.playListRepository = playListRepository;
        this.professeurRepository = professeurRepository;
    }

    // Student requests access
    public LearnPlaylistResponse requestAccess(LearnPlaylistRequest request) {
        Etudiant student = etudiantRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        PlayList playlist = playListRepository.findById(request.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Professeur professor = playlist.getProfesseur(); // assuming playlist has professor

        LearnPlaylist learnPlaylist = new LearnPlaylist(student, playlist, professor);
        learnPlaylist.setVerified(false); // default

        LearnPlaylist saved = repository.save(learnPlaylist);

        return mapToResponse(saved);
    }

    // Fetch all students of a professor
    public List<LearnPlaylistResponse> getAllStudentsOfProfessor(Long profId) {
        return repository.findByProfessor_Id(profId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Fetch pending students
    public List<LearnPlaylistResponse> getPendingStudentsOfProfessor(Long profId) {
        return repository.findByProfessor_IdAndVerifiedFalse(profId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Fetch verified students
    public List<LearnPlaylistResponse> getVerifiedStudentsOfProfessor(Long profId) {
        return repository.findByProfessor_IdAndVerifiedTrue(profId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Verify a student
    public LearnPlaylistResponse verifyStudent(Long learnPlaylistId) {
        LearnPlaylist learnPlaylist = repository.findById(learnPlaylistId)
                .orElseThrow(() -> new RuntimeException("LearnPlaylist not found"));

        learnPlaylist.setVerified(true);
        return mapToResponse(repository.save(learnPlaylist));
    }

    // Mapping entity -> DTO
    private LearnPlaylistResponse mapToResponse(LearnPlaylist lp) {
        return LearnPlaylistResponse.builder()
                .id(lp.getId())
                .studentId(lp.getEtudiant().getId())
                .playlistId(lp.getPlaylist().getId())
                .professorId(lp.getProfessor().getId())
                .verified(lp.isVerified())
                .build();
    }
}
