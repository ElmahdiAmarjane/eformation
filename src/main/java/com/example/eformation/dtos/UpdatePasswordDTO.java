package com.example.eformation.dtos;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String email;
    private String newPassword;
}
