package com.example.eformation.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")
@Table(name = "admin")
@Data
@NoArgsConstructor  // Required by JPA
public class Admin extends User {

    public Admin(String fullName, String email, String password,String codeOtp) {
        super(fullName, email, password, Role.ADMIN, codeOtp);
    }
}
