package com.example.eformation.models;

import jakarta.persistence.*;
import com.example.eformation.models.user.*;
import com.example.eformation.models.playlist.*;;
@Entity
@Table(name = "learn_playlist")
public class LearnPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private PlayList playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prof_id", nullable = false)
    private Professeur professor;

    @Column(nullable = false)
    private boolean verified = false;

    public LearnPlaylist() {}

    public LearnPlaylist(Etudiant student, PlayList playlist, Professeur professor) {
        this.etudiant = student;
        this.playlist = playlist;
        this.professor = professor;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public Etudiant getEtudiant() { return etudiant; }
    public void setStudent(Etudiant student) { this.etudiant = etudiant; }

    public PlayList getPlaylist() { return playlist; }
    public void setPlaylist(PlayList playlist) { this.playlist = playlist; }

    public Professeur getProfessor() { return professor; }
    public void setProfessor(Professeur professor) { this.professor = professor; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
}
