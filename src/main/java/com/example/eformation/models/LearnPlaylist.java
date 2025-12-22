package com.example.eformation.models;

import com.example.eformation.models.playlist.PlayList;
import com.example.eformation.models.user.Etudiant;
import com.example.eformation.models.user.Professeur;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LearnPlaylist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;
    
    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private PlayList playlist;
    
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)  // Column name is professor_id
    private Professeur professor;  // Field name is professor (with 'o')
    
    private boolean verified = false;
    
    // Constructor
    public LearnPlaylist(Etudiant etudiant, PlayList playlist, Professeur professor) {
        this.etudiant = etudiant;
        this.playlist = playlist;
        this.professor = professor;
    }
}