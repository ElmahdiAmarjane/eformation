package com.example.eformation.dtos;

import com.example.eformation.models.user.Role;

import lombok.Data;

@Data
public class LoginResponse {
    private String fullName;
    private String email;
    private Role role;
    private boolean isProfVerified;
    private String token;
}
