package com.example.eformation.dtos.LearnPlaylist;

import lombok.Data;

@Data
public class SendInvitationRequest {
    private String studentEmail;   
    private Long playlistId;       
    private Long professorId;      
}