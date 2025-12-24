package com.example.eformation.dtos.LearnPlaylist;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LearnPlaylistStudentResponse {

    private Long studentId;
    private String studentName;
    private String studentEmail;

    private Long playlistId;
    private String playlistTitle;

    private boolean verified;
}
