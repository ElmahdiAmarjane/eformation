package com.example.eformation.services;

import com.example.eformation.dtos.Pack.PackRequest;
import com.example.eformation.dtos.Pack.PackResponse;
import com.example.eformation.models.Packs.Pack;
import com.example.eformation.repository.PackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackService {

    private final PackRepository packRepository;

    // 🟩 Récupérer tous les packs
    public List<PackResponse> getAllPacks() {
        return packRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 🟩 Récupérer un pack par ID
    public PackResponse getPackById(Long id) {
        Pack pack = packRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack not found ❌"));
        return toResponse(pack);
    }

    // 🟦 Ajouter un nouveau pack
    public PackResponse addPack(PackRequest request) {
        if (packRepository.existsByType(request.getType())) {
            throw new RuntimeException("This pack type already exists");
        }

        Pack pack = new Pack();
        pack.setType(request.getType());
        pack.setDescription(request.getDescription());
        pack.setOld_price(request.getOld_price());
        pack.setNew_price(request.getNew_price());
        pack.setCount_playlst(request.getCount_playlst());
        pack.setFeatures(request.getFeatures());

        Pack saved = packRepository.save(pack);
        return toResponse(saved);
    }

    // 🟨 Mettre à jour un pack
    public PackResponse updatePack(Long id, PackRequest request) {
        Pack pack = packRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack not found ❌"));

        pack.setType(request.getType());
        pack.setDescription(request.getDescription());
        pack.setOld_price(request.getOld_price());
        pack.setNew_price(request.getNew_price());
        pack.setFeatures(request.getFeatures());

        Pack updated = packRepository.save(pack);
        return toResponse(updated);
    }

    // 🟥 Supprimer un pack
    public void deletePack(Long id) {
        if (!packRepository.existsById(id)) {
            throw new RuntimeException("Pack not found ❌");
        }
        packRepository.deleteById(id);
    }

    // 🧠 Mapping interne (convertir Pack → PackResponse)
    private PackResponse toResponse(Pack pack) {
        PackResponse response = new PackResponse();
        response.setId(pack.getId());
        response.setType(pack.getType());
        response.setDescription(pack.getDescription());
        response.setOld_price(pack.getOld_price());
        response.setNew_price(pack.getNew_price());
        response.setCount_playlst(pack.getCount_playlst());
        response.setFeatures(pack.getFeatures());
        return response;
    }
}
