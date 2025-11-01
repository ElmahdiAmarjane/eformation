package com.example.eformation.dtos;

import com.example.eformation.models.user.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest  {

    private String fullName;
    private String email;
    private String password;
    private Role role; 
    private String uniquePath; 
    private Long packId; 

}
