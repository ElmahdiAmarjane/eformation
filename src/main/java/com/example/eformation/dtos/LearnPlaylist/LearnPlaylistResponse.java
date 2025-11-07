package com.example.eformation.dtos.LearnPlaylist;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LearnPlaylistResponse {
    private Long id;
    private Long studentId;
    private Long playlistId;
    private Long professorId;
    private boolean verified;
}
