package com.example.eformation.models.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("PROFESSEUR")
@Table(name = "professeur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professeur extends User {

    private boolean isVerifiedByAdmin;
    
    @Column(unique = true)
    private String uniquePath;

    public Professeur(String fullName, String email, String password, Role role, String uniquePath,String codeOtp) {
        super(fullName, email, password, role,codeOtp);
        this.isVerifiedByAdmin = false;
        this.uniquePath = uniquePath;
    }
}

