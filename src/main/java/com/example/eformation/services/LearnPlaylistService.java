package com.example.eformation.services;

import com.example.eformation.dtos.LearnPlaylist.*;
import com.example.eformation.models.LearnPlaylist;
import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.models.user.Etudiant;
import com.example.eformation.models.user.Professeur;
import com.example.eformation.models.user.User;
import com.example.eformation.repository.LearnPlaylistRepository;
import com.example.eformation.repository.EtudiantRepository;
import com.example.eformation.repository.PlayListRepository;
import com.example.eformation.repository.ProfesseurRepository;
import com.example.eformation.repository.UserRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LearnPlaylistService {

    private final LearnPlaylistRepository learnPlaylistRepository;
    private final EtudiantRepository etudiantRepository;
    private final PlayListRepository playListRepository;
    private final ProfesseurRepository professeurRepository;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final EmailService emailService;


    public LearnPlaylistService(
            JavaMailSender mailSender,
            UserRepository userRepository,
            LearnPlaylistRepository learnPlaylistRepository,
            EtudiantRepository etudiantRepository,
            PlayListRepository playListRepository,
            EmailService emailService,
            ProfesseurRepository professeurRepository) {
        this.learnPlaylistRepository = learnPlaylistRepository;
        this.etudiantRepository = etudiantRepository;
        this.playListRepository = playListRepository;
        this.professeurRepository = professeurRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.emailService=emailService;
    }

    // Save (student requests access) — unchanged behaviour, but return rich DTO
    @Transactional
    public LearnPlaylistResponse requestAccess(Long studentId, Long playlistId) {
        Etudiant student = etudiantRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        PlayList playlist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Professeur professor = playlist.getProfesseur(); // assuming playlist has getProfessor()

        LearnPlaylist lp = new LearnPlaylist(student, playlist, professor);
        lp.setVerified(false);

        LearnPlaylist saved = learnPlaylistRepository.save(lp);
        return mapToResponse(saved);
    }

    public List<LearnPlaylistResponse> getAllStudentsOfProfessor(Long profId) {
        return learnPlaylistRepository.findByProfessor_Id(profId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<LearnPlaylistResponse> getPendingStudentsOfProfessor(Long profId) {
        return learnPlaylistRepository.findByProfessor_IdAndVerifiedFalse(profId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<LearnPlaylistResponse> getVerifiedStudentsOfProfessor(Long profId) {
        return learnPlaylistRepository.findByProfessor_IdAndVerifiedTrue(profId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public LearnPlaylistResponse verifyStudent(Long learnPlaylistId) {
        LearnPlaylist learnPlaylist = learnPlaylistRepository.findById(learnPlaylistId)
                .orElseThrow(() -> new RuntimeException("LearnPlaylist not found"));
        learnPlaylist.setVerified(true);
        LearnPlaylist saved = learnPlaylistRepository.save(learnPlaylist);
        return mapToResponse(saved);
    }

    // Map entity -> DTO (rich)
    private LearnPlaylistResponse mapToResponse(LearnPlaylist lp) {
        Etudiant e = lp.getEtudiant();
        PlayList p = lp.getPlaylist();
        Professeur prof = lp.getProfessor();

        LearnPlaylistResponse.StudentInfo studentInfo = LearnPlaylistResponse.StudentInfo.builder()
                .id(e.getId())
                .fullName(getSafeFullName(e))
                .email(getSafeEmail(e))
                .build();

        LearnPlaylistResponse.PlaylistInfo playlistInfo = LearnPlaylistResponse.PlaylistInfo.builder()
                .id(p.getId())
                .titre(getPlaylistTitleSafe(p))
                .description(getPlaylistDescriptionSafe(p))
                .build();

        LearnPlaylistResponse.ProfessorInfo professorInfo = LearnPlaylistResponse.ProfessorInfo.builder()
                .id(prof != null ? prof.getId() : null)
                .fullName(prof != null ? getSafeFullName(prof) : null)
                .email(prof != null ? getSafeEmail(prof) : null)
                .build();

        return LearnPlaylistResponse.builder()
                .id(lp.getId())
                .verified(lp.isVerified())
                .student(studentInfo)
                .playlist(playlistInfo)
                .professor(professorInfo)
                .build();
    }

    // Helper safe getters — adapt if your methods differ
    private String getSafeFullName(Object userEntity) {
        try {
            // Etudiant and Professeur extend User with getFullName()
            return (String) userEntity.getClass().getMethod("getFullName").invoke(userEntity);
        } catch (Exception ex) {
            return null;
        }
    }

    private String getSafeEmail(Object userEntity) {
        try {
            return (String) userEntity.getClass().getMethod("getEmail").invoke(userEntity);
        } catch (Exception ex) {
            return null;
        }
    }

    private String getPlaylistTitleSafe(PlayList p) {
        try {
            // try getTitre() first (French) then getTitle() (English)
            try {
                return (String) p.getClass().getMethod("getTitre").invoke(p);
            } catch (NoSuchMethodException ignore) { }
            try {
                return (String) p.getClass().getMethod("getTitle").invoke(p);
            } catch (NoSuchMethodException ignore) { }
            try {
                return (String) p.getClass().getMethod("getName").invoke(p);
            } catch (NoSuchMethodException ignore) { }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    private String getPlaylistDescriptionSafe(PlayList p) {
        try {
            return (String) p.getClass().getMethod("getDescription").invoke(p);
        } catch (Exception ex) {
            return null;
        }
    }

    public LearnPlaylistResponse updateVerification(Long learnPlaylistId, boolean verified) {
        LearnPlaylist learnPlaylist = learnPlaylistRepository.findById(learnPlaylistId)
                .orElseThrow(() -> new RuntimeException("LearnPlaylist not found"));
    
        learnPlaylist.setVerified(verified);
        LearnPlaylist updated = learnPlaylistRepository.save(learnPlaylist);
    
        return mapToResponse(updated);
    }

    public void deleteLearnPlaylist(Long id) {
        if (!learnPlaylistRepository.existsById(id)) {
            throw new RuntimeException("LearnPlaylist not found with id: " + id);
        }
        learnPlaylistRepository.deleteById(id);
    }

    //SEND EMAIL
    public void sendInvitation(SendInvitationRequest request) {
        // Optional: fetch prof and playlist info from DB to include in email
        User professor = userRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));
    
        // You could fetch playlist details too if needed
        String playlistTitle = "Your Formation"; // Replace with actual fetch from repository
    
        // Construct email message
        String subject = "Invitation to join formation";
        String text = String.format(
                "Bonjour,\n\nVous avez été invité à rejoindre la formation '%s' par le professeur %s.\n\nMerci.",
                playlistTitle,
                professor.getFullName()
        );
    
        // Send email
        emailService.sendEmail(request.getStudentEmail(), subject, text);
    }
    
}
