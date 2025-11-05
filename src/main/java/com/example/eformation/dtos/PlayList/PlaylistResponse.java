package com.example.eformation.dtos.PlayList;

import com.example.eformation.models.user.Professeur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistResponse {
    private Long id;
    private String title;
    private String description;
    private String visibility;
    private String miniature;
    private LocalDateTime dateCreation;
    private Professeur professeur;
}
