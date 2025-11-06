package com.example.eformation.dtos.chapitre;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapitreResponse {
    private Long id;
    private String titre;
    private LocalDateTime datePublication;
    private Long playlistId;
    // private List<ElementResponse> elements; 
}
