package com.example.eformation.dtos.LearnPlaylist;


import lombok.Data;

@Data
public class LearnPlaylistRequest {
    private Long studentId;
    private Long playlistId;
}
