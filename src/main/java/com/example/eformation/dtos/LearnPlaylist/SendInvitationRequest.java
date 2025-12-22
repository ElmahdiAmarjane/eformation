package com.example.eformation.dtos.LearnPlaylist;

import lombok.Data;

@Data
public class SendInvitationRequest {
    private String studentEmail;   
    private String studentName;
    private String loginLink;
    private Long playlistId;       
    private Long professorId;      
}