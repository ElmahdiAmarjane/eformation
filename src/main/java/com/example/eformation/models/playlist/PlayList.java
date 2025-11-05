package com.example.eformation.models.playlist;

import com.example.eformation.models.user.Professeur;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(nullable = false)
    private String visibility; // "public" or "private"

    private String miniature; // image URL or path

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "professeur_id", nullable = false)
    private Professeur professeur;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapitre> chapitres;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
    }
}
