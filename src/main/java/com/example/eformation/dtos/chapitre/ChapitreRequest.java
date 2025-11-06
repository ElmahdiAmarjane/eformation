package com.example.eformation.dtos.chapitre;

import lombok.Data;

@Data
public class ChapitreRequest {
    private String titre;
    private Long playlistId; // foreign key
}
