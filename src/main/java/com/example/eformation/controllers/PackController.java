package com.example.eformation.controllers;

import com.example.eformation.dtos.Pack.PackRequest;
import com.example.eformation.dtos.Pack.PackResponse;
import com.example.eformation.services.PackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PackController {

    private final PackService packService;

    @GetMapping("/all")
    public ResponseEntity<List<PackResponse>> getAllPacks() {
        return ResponseEntity.ok(packService.getAllPacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackResponse> getPackById(@PathVariable Long id) {
        return ResponseEntity.ok(packService.getPackById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<PackResponse> addPack(@RequestBody PackRequest request) {
        return ResponseEntity.ok(packService.addPack(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PackResponse> updatePack(
            @PathVariable Long id,
            @RequestBody PackRequest request
    ) {
        return ResponseEntity.ok(packService.updatePack(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePack(@PathVariable Long id) {
        packService.deletePack(id);
        return ResponseEntity.ok("Pack deleted successfully ✅");
    }
}
