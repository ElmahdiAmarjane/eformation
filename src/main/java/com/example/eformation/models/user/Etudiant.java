package com.example.eformation.models.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("ETUDIANT")
@Table(name = "etudiant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant extends User {

    private boolean isVerfiedByProf;

    public Etudiant(String fullName, String email, String password,  String codeOtp) {
        super(fullName, email, password, Role.ETUDIANT, codeOtp);
        this.isVerfiedByProf = false;

    }
    
}
