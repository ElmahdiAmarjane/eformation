package com.example.eformation.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.eformation.dtos.SignupRequest;
import com.example.eformation.models.Admin;
import com.example.eformation.models.Etudiant;
import com.example.eformation.models.Professeur;
import com.example.eformation.models.User;
import com.example.eformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    public  User signup(SignupRequest request) {


        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user;
        switch (request.getRole()) {
            case PROFESSEUR -> user = new Professeur(request.getFullName(),request.getEmail(),request.getPassword(),request.getRole(),request.getUniquePath());
            case ETUDIANT -> user = new Etudiant(request.getFullName(),request.getEmail(),request.getPassword(),request.getRole());
            default ->  user = new User(request.getFullName(),request.getEmail(),request.getPassword(),request.getRole());
        }

        return userRepository.save(user);
    }
}
