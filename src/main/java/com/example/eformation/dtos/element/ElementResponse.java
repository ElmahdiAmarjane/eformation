package com.example.eformation.dtos.element;


import com.example.eformation.models.playlist.ElementType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElementResponse {
    private Long id;
    private String titre;
    private String description;
    private String miniature;
    private String lien;
    private ElementType type;
    private Long chapitreId;
}
