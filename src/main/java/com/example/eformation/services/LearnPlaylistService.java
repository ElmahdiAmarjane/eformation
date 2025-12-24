package com.example.eformation.services;

import com.example.eformation.dtos.LearnPlaylist.LearnPlaylistStudentResponse;
import com.example.eformation.dtos.LearnPlaylist.SendInvitationRequest;
import com.example.eformation.models.LearnPlaylist;
import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.models.user.Etudiant;
import com.example.eformation.models.user.Professeur;
import com.example.eformation.models.user.Role;
import com.example.eformation.repository.EtudiantRepository;
import com.example.eformation.repository.LearnPlaylistRepository;
import com.example.eformation.repository.PlayListRepository;
import com.example.eformation.repository.ProfesseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearnPlaylistService {

    private final EtudiantRepository etudiantRepository;
    private final ProfesseurRepository professeurRepository;
    private final PlayListRepository playListRepository;
    private final LearnPlaylistRepository learnPlaylistRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public LearnPlaylist sendInvitation(SendInvitationRequest request) {

        Professeur professor = professeurRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        PlayList playlist = playListRepository.findById(request.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Etudiant student = etudiantRepository.findByEmail(request.getStudentEmail())
                .orElseGet(() -> {
                    String tempPassword = generateTempPassword();
                    Etudiant s = new Etudiant();
                    s.setEmail(request.getStudentEmail());
                    s.setFullName(request.getStudentName());
                    s.setPassword(passwordEncoder.encode(tempPassword));
                    s.setActive(true);
                    s.setRole(Role.ETUDIANT);

                    Etudiant savedStudent = etudiantRepository.saveAndFlush(s);

                    emailService.sendEmail(
                            savedStudent.getEmail(),
                            "Bienvenue sur le système",
                            buildEmail(savedStudent, professor, playlist, request.getLoginLink(), tempPassword, true)
                    );

                    return savedStudent;
                });

        LearnPlaylist learnPlaylist = learnPlaylistRepository
                .findByEtudiantIdAndPlaylistId(student.getId(), playlist.getId())
                .orElseGet(() -> {
                    LearnPlaylist lp = new LearnPlaylist(student, playlist, professor);
                    lp.setVerified(false); // default pending
                    return learnPlaylistRepository.save(lp);
                });

        // Send email about playlist access if student already existed
        if (etudiantRepository.findByEmail(request.getStudentEmail()).isPresent()) {
            emailService.sendEmail(
                    student.getEmail(),
                    "Accès à la playlist",
                    buildEmail(student, professor, playlist, request.getLoginLink(), null, false)
            );
        }

        return learnPlaylist;
    }

    public List<LearnPlaylistStudentResponse> getStudentsByProfessor(Long professorId) {
        professeurRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        return learnPlaylistRepository.findByProfessorId(professorId)
                .stream()
                .map(lp -> LearnPlaylistStudentResponse.builder()
                        .studentId(lp.getEtudiant().getId())
                        .studentName(lp.getEtudiant().getFullName())
                        .studentEmail(lp.getEtudiant().getEmail())
                        .playlistId(lp.getPlaylist().getId())
                        .playlistTitle(lp.getPlaylist().getTitle())
                        .verified(lp.isVerified())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<LearnPlaylistStudentResponse> getPlaylistsOfStudent(Long studentId) {
        etudiantRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return learnPlaylistRepository.findByEtudiantId(studentId)
                .stream()
                .map(lp -> LearnPlaylistStudentResponse.builder()
                        .studentId(lp.getEtudiant().getId())
                        .studentName(lp.getEtudiant().getFullName())
                        .studentEmail(lp.getEtudiant().getEmail())
                        .playlistId(lp.getPlaylist().getId())
                        .playlistTitle(lp.getPlaylist().getTitle())
                        .verified(lp.isVerified())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public LearnPlaylist verifyStudentInPlaylist(Long learnPlaylistId, boolean verified) {
        LearnPlaylist lp = learnPlaylistRepository.findById(learnPlaylistId)
                .orElseThrow(() -> new RuntimeException("LearnPlaylist entry not found"));

        lp.setVerified(verified);
        return learnPlaylistRepository.save(lp);
    }

    // ========================
    // HELPERS
    // ========================
    private String generateTempPassword() {
        return "Pass@" + (int)(Math.random() * 1_000_000);
    }

    private String buildEmail(Etudiant student, Professeur professor, PlayList playlist, String loginLink, String tempPassword, boolean isNewStudent) {
        if (isNewStudent) {
            return String.format(
                    "Bonjour %s,\n\nVous avez été enregistré par le professeur %s.\n" +
                    "Vous avez maintenant accès à la playlist \"%s\".\n\n" +
                    "Email: %s\nMot de passe temporaire: %s\nLien de connexion: %s\n\nMerci.",
                    student.getFullName(),
                    professor.getFullName(),
                    playlist.getTitle(),
                    student.getEmail(),
                    tempPassword,
                    loginLink != null ? loginLink : "http://localhost:3000/login"
            );
        } else {
            return String.format(
                    "Bonjour %s,\n\nLe professeur %s vous a donné accès à la playlist \"%s\".\n" +
                    "Email: %s\nLien de connexion: %s\n\nMerci.",
                    student.getFullName(),
                    professor.getFullName(),
                    playlist.getTitle(),
                    student.getEmail(),
                    loginLink != null ? loginLink : "http://localhost:3000/login"
            );
        }
    }
}
