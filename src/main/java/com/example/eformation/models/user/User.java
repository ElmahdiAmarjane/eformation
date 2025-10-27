package com.example.eformation.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false, length = 100)
    private String fullName;

    @JsonIgnore
    @Column(nullable = false, unique = false)
    private String password;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; 

    @Column(name = "code_otp", length = 6)
    private String codeOtp;

    @Column(nullable = false)
    private boolean active = false;

    public User(String fullName, String email, String password,  Role role, String codeOtp) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = false;
        this.codeOtp=codeOtp;
    }

    public User orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }
}
