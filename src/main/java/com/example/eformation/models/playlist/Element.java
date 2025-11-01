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

    private String titre;
    private String lien; 

    @Enumerated(EnumType.STRING)
    private ElementType type;

    @ManyToOne
    @JoinColumn(name = "chapitre_id", nullable = false)
    private Chapitre chapitre;
}
