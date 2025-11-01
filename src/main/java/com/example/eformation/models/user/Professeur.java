package com.example.eformation.models.user;

import java.util.List;

import com.example.eformation.models.Packs.Pack;
import com.example.eformation.models.playlist.PlayList;

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
    
    @Column(unique = true, nullable = true)
    private String uniquePath;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack  pack;

    public Professeur(String fullName, String email, String password, Role role, String uniquePath,String codeOtp,Pack  pack) {
        super(fullName, email, password, role,codeOtp);
        this.isVerifiedByAdmin = false;
        this.uniquePath = uniquePath;
        this.pack = pack;
    }

    public boolean isProfVerified() {
        return isVerifiedByAdmin;
    }
}

