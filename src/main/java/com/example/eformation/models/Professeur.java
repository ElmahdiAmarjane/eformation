package com.example.eformation.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("PROFESSEUR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professeur extends User {

    private boolean isVerifiedByAdmin;
    
    @Column(unique = true)
    private String uniquePath;

    public Professeur(String fullName, String email, String password, Role role, String uniquePath) {
        super(fullName, email, password, role);
        this.isVerifiedByAdmin = false;
        this.uniquePath = uniquePath;
    }
}

