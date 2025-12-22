package com.example.eformation.dtos.Login;

import com.example.eformation.models.user.Role;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private boolean isProfVerified;
    private String token;
}
