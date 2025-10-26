package com.example.eformation.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("ETUDIANT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant extends User {

    private boolean isVerfiedByProf;

    public Etudiant(String fullName, String email, String password, Role role) {
        super(fullName, password, email, role);
        this.isVerfiedByProf=false;
    }
}
