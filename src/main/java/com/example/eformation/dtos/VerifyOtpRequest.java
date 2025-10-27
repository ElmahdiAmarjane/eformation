package com.example.eformation.dtos;

public class VerifyOtpRequest {
    private String email;
    private String codeOtp;

    public String getEmail(){
        return email;
    }

    public String getCodeOtp(){
        return codeOtp;
    }
}
