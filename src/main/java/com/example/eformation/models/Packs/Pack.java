package com.example.eformation.models.Packs;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PackType type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double old_price;

    @Column(nullable = false)
    private double new_price;

    @Column(nullable = false)
    private int count_playlst;

    @ElementCollection
    @CollectionTable(name = "pack_features", joinColumns = @JoinColumn(name = "pack_id"))
    @Column(name = "feature")
    private List<String> features;
}
