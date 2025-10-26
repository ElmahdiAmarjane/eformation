package com.example.eformation.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("ADMIN")
@Data
@NoArgsConstructor      // generates default no-args constructor
public class Admin extends User {
    // No need for @AllArgsConstructor or manual constructors
}
