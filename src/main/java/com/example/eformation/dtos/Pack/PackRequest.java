package com.example.eformation.dtos.Pack;

import lombok.Data;
import java.util.List;

import com.example.eformation.models.Packs.PackType;

@Data
public class PackRequest {
    private PackType type;
    private String description;
    private double old_price;
    private double new_price;
    private int count_playlst;
    private List<String> features;
}
