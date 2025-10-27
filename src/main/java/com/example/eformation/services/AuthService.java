package com.example.eformation.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.eformation.dtos.LoginRequest;
import com.example.eformation.dtos.LoginResponse;
import com.example.eformation.dtos.PasswordResetRequestDTO;
import com.example.eformation.dtos.SignupRequest;
import com.example.eformation.dtos.UpdatePasswordDTO;
import com.example.eformation.dtos.VerifyOtpRequest;
import com.example.eformation.models.user.Admin;
import com.example.eformation.models.user.Etudiant;
import com.example.eformation.models.user.Professeur;
import com.example.eformation.models.user.User;
import com.example.eformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    //SIGNUP
    public  User signup(SignupRequest request) {


        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user;
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        String otpCode = String.format("%06d", new java.util.Random().nextInt(999999));

        switch (request.getRole()) {
            case PROFESSEUR -> user = new Professeur(request.getFullName(),request.getEmail(),hashedPassword,request.getRole(),request.getUniquePath(),otpCode);
            case ETUDIANT -> user = new Etudiant(request.getFullName(),request.getEmail(),hashedPassword,request.getRole(),otpCode);
            default ->  user = new Admin(request.getFullName(),request.getEmail(),hashedPassword,otpCode);
        }

        User savedUser = userRepository.save(user);
        emailService.sendOtpEmail(savedUser.getEmail(), otpCode);
        return savedUser;
    }

    //Verify
    public boolean verifyOtp(VerifyOtpRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getCodeOtp().equals(req.getCodeOtp())) {
            return false;
        }

        user.setActive(true);
        user.setCodeOtp(null); // delete after verification
        userRepository.save(user);
        return true;
    }

    //LOGIN
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("User not verified");
        }

        // Construire la réponse
        LoginResponse response = new LoginResponse();
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }

    //Send Reset Password Request
    public String requestPasswordReset(PasswordResetRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        String otpCode = String.format("%06d", new java.util.Random().nextInt(999999));
        user.setCodeOtp(otpCode);

        userRepository.save(user);
        emailService.sendOtpEmail(user.getEmail(), otpCode); // vous l'avez déjà

        return "OTP sent successfully";
    }

    //VERIFY OTP CODE
    public boolean verifyOtpForReset(VerifyOtpRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        return user.getCodeOtp() != null && user.getCodeOtp().equals(req.getCodeOtp());
    }

    //RESET PASSWORD
    public String updatePassword(UpdatePasswordDTO req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        user.setCodeOtp(null); // supprimer OTP après utilisation
        userRepository.save(user);

        return "Password updated successfully";
    }



}
