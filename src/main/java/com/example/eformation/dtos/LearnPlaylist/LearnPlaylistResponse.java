package com.example.eformation.dtos.LearnPlaylist;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LearnPlaylistResponse {
    private Long id;
    private boolean verified;
    private StudentInfo student;
    private PlaylistInfo playlist;
    private ProfessorInfo professor;

    @Data
    @Builder
    public static class StudentInfo {
        private Long id;
        private String fullName;
        private String email;
        // add other student fields you need (e.g. phone, avatar) and map them in service
    }

    @Data
    @Builder
    public static class PlaylistInfo {
        private Long id;
        private String titre; // or "title" if your playlist uses English name
        private String description; // optional, if playlist has it
        // add other playlist fields you need
    }

    @Data
    @Builder
    public static class ProfessorInfo {
        private Long id;
        private String fullName;
        private String email;
        // add other professor fields if needed
    }
}
