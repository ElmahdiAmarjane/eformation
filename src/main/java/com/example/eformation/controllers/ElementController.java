package com.example.eformation.controllers;

import com.example.eformation.dtos.element.*;
import com.example.eformation.services.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elements")
@RequiredArgsConstructor
public class ElementController {

    private final ElementService elementService;

    @PostMapping("/create")
    public ResponseEntity<ElementResponse> create(@RequestBody ElementRequest request) {
        return ResponseEntity.ok(elementService.createElement(request));
    }

    @GetMapping("/chapitre/{chapitreId}")
    public ResponseEntity<List<ElementResponse>> getByChapitre(@PathVariable Long chapitreId) {
        return ResponseEntity.ok(elementService.getElementsByChapitre(chapitreId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ElementResponse> update(@PathVariable Long id, @RequestBody ElementRequest request) {
        return ResponseEntity.ok(elementService.updateElement(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String message = elementService.deleteElement(id);
        return ResponseEntity.ok(message);
    }
}

