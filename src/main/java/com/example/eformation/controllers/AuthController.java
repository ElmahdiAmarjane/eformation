package com.example.eformation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eformation.dtos.LoginRequest;
import com.example.eformation.dtos.LoginResponse;
import com.example.eformation.dtos.PasswordResetRequestDTO;
import com.example.eformation.dtos.SignupRequest;
import com.example.eformation.dtos.UpdatePasswordDTO;
import com.example.eformation.dtos.VerifyOtpRequest;
import com.example.eformation.models.user.User;
import com.example.eformation.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
        return authService.verifyOtp(request)
            ? ResponseEntity.ok("Account verified")
            : ResponseEntity.status(400).body("Invalid OTP");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<String> requestReset(@RequestBody PasswordResetRequestDTO request) {
        return ResponseEntity.ok(authService.requestPasswordReset(request));
    }

    @PostMapping("/verify-reset-otp")
    public ResponseEntity<Boolean> verifyResetOtp(@RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok(authService.verifyOtpForReset(request));
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO request) {
        return ResponseEntity.ok(authService.updatePassword(request));
    }


}
