package com.example.eformation.models.playlist;

import com.example.eformation.models.user.Professeur;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(nullable = false)
    private String visibility; // public / private

    private String miniature;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    // ==============================
    // IMPORTANT PART (fix)
    // ==============================
    @ManyToOne
    @JoinColumn(name = "professeur_id", nullable = false)
    private Professeur professeur;
    // 🔑 field name is "professor" → getter = getProfessor()

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapitre> chapitres;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
    }
}
