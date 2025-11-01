package com.example.eformation.dtos.PlayList;

import com.example.eformation.models.user.Professeur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistResponse {
    private Long id;
    private String title;
    private String description;
    private Professeur professeur; // or only name/email if you prefer light data
}
