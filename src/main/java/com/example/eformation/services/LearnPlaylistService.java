package com.example.eformation.services;

import com.example.eformation.dtos.LearnPlaylist.*;
import com.example.eformation.models.LearnPlaylist;
import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.models.user.Etudiant;
import com.example.eformation.models.user.Professeur;
import com.example.eformation.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearnPlaylistService {

    private final LearnPlaylistRepository learnPlaylistRepository;
    private final EtudiantRepository etudiantRepository;
    private final PlayListRepository playListRepository;
    private final ProfesseurRepository professeurRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // =====================================================
    // REQUEST ACCESS (student → playlist)
    // =====================================================
    @Transactional
    public LearnPlaylistResponse requestAccess(Long studentId, Long playlistId) {
        Etudiant student = etudiantRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        PlayList playlist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Professeur professor = playlist.getProfesseur();

        LearnPlaylist lp = learnPlaylistRepository
                .findByEtudiantIdAndPlaylistId(studentId, playlistId)
                .orElseGet(() -> {
                    LearnPlaylist newLp = new LearnPlaylist(student, playlist, professor);
                    newLp.setVerified(false);
                    return learnPlaylistRepository.save(newLp);
                });

        return mapToResponse(lp);
    }

    // =====================================================
    // GET STUDENTS
    // =====================================================
    public List<LearnPlaylistResponse> getAllStudentsOfProfessor(Long profId) {
        return learnPlaylistRepository.findByProfessorId(profId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<LearnPlaylistResponse> getPendingStudentsOfProfessor(Long profId) {
        return learnPlaylistRepository.findByProfessorIdAndVerifiedFalse(profId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<LearnPlaylistResponse> getVerifiedStudentsOfProfessor(Long profId) {
        return learnPlaylistRepository.findByProfessorIdAndVerifiedTrue(profId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =====================================================
    // VERIFY / UPDATE
    // =====================================================
    @Transactional
    public LearnPlaylistResponse updateVerification(Long learnPlaylistId, boolean verified) {
        LearnPlaylist lp = learnPlaylistRepository.findById(learnPlaylistId)
                .orElseThrow(() -> new RuntimeException("LearnPlaylist not found"));

        lp.setVerified(verified);
        return mapToResponse(learnPlaylistRepository.save(lp));
    }

    // =====================================================
    // DELETE
    // =====================================================
    @Transactional
    public void deleteLearnPlaylist(Long id) {
        if (!learnPlaylistRepository.existsById(id)) {
            throw new RuntimeException("LearnPlaylist not found");
        }
        learnPlaylistRepository.deleteById(id);
    }

    // =====================================================
    // SEND INVITATION
    // =====================================================
    @Transactional
    public LearnPlaylistResponse sendInvitation(SendInvitationRequest request) {
        Professeur professor = professeurRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        PlayList playlist = playListRepository.findById(request.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Etudiant student = etudiantRepository.findByEmail(request.getStudentEmail())
                .orElseGet(() -> {
                    Etudiant s = new Etudiant();
                    s.setEmail(request.getStudentEmail());
                    s.setFullName(request.getStudentName());
                    String tempPassword = generateTempPassword();
                    s.setPassword(passwordEncoder.encode(tempPassword));
                    s.setActive(true);
                    Etudiant saved = etudiantRepository.save(s);

                    emailService.sendEmail(
                            saved.getEmail(),
                            "Invitation to join playlist",
                            buildInvitationEmail(saved, professor, playlist, tempPassword, request.getLoginLink())
                    );
                    return saved;
                });

        LearnPlaylist lp = learnPlaylistRepository
                .findByEtudiantIdAndPlaylistId(student.getId(), playlist.getId())
                .orElseGet(() -> {
                    LearnPlaylist link = new LearnPlaylist(student, playlist, professor);
                    link.setVerified(true);
                    return learnPlaylistRepository.save(link);
                });

        return mapToResponse(lp);
    }

    // =====================================================
    // HELPERS
    // =====================================================
    private String generateTempPassword() {
        return "Pass@" + (int) (Math.random() * 1_000_000);
    }

    private String buildInvitationEmail(
            Etudiant student,
            Professeur professor,
            PlayList playlist,
            String password,
            String loginLink
    ) {
        return String.format(
                "Bonjour %s,\n\nVous êtes invité à rejoindre la playlist \"%s\" par le professeur %s.\n\n" +
                        "Email: %s\nMot de passe temporaire: %s\nLien de connexion: %s\n\nMerci.",
                student.getFullName(),
                playlist.getTitle(),
                professor.getFullName(),
                student.getEmail(),
                password,
                loginLink != null ? loginLink : "http://localhost:3000/login"
        );
    }

    private LearnPlaylistResponse mapToResponse(LearnPlaylist lp) {
        return LearnPlaylistResponse.builder()
                .id(lp.getId())
                .verified(lp.isVerified())
                .student(LearnPlaylistResponse.StudentInfo.builder()
                        .id(lp.getEtudiant().getId())
                        .fullName(lp.getEtudiant().getFullName())
                        .email(lp.getEtudiant().getEmail())
                        .build())
                .playlist(LearnPlaylistResponse.PlaylistInfo.builder()
                        .id(lp.getPlaylist().getId())
                        .titre(lp.getPlaylist().getTitle())
                        .description(lp.getPlaylist().getDescription())
                        .build())
                .professor(LearnPlaylistResponse.ProfessorInfo.builder()
                        .id(lp.getProfessor().getId())  // Changed from getProfesseur() to getProfessor()
                        .fullName(lp.getProfessor().getFullName())
                        .email(lp.getProfessor().getEmail())
                        .build())
                .build();
    }
}