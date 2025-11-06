package com.example.eformation.models.playlist;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;
    @Column(nullable = false)
    private String lien; 

    private String description; 

    private String miniature; 

    @Enumerated(EnumType.STRING)
    private ElementType type;

    @ManyToOne
    @JoinColumn(name = "chapitre_id", nullable = false)
    private Chapitre chapitre;
}
