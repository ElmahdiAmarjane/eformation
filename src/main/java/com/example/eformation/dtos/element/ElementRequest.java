package com.example.eformation.dtos.element;

import com.example.eformation.models.playlist.ElementType;
import lombok.Data;

@Data
public class ElementRequest {
    private String titre;
    private String description;
    private String miniature;
    private String lien;
    private ElementType type;
    private Long chapitreId; // link to Chapitre
}

