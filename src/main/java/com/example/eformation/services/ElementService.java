package com.example.eformation.services;

import com.example.eformation.dtos.element.*;
import com.example.eformation.models.playlist.Chapitre;
import com.example.eformation.models.playlist.Element;
import com.example.eformation.repository.ChapitreRepository;
import com.example.eformation.repository.ElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElementService {

    private final ElementRepository elementRepository;
    private final ChapitreRepository chapitreRepository;

    // ➕ Create new element
    public ElementResponse createElement(ElementRequest request) {
        Chapitre chapitre = chapitreRepository.findById(request.getChapitreId())
                .orElseThrow(() -> new RuntimeException("Chapitre not found"));

        Element element = new Element();
        element.setTitre(request.getTitre());
        element.setDescription(request.getDescription());
        element.setLien(request.getLien());
        element.setType(request.getType());
        element.setChapitre(chapitre);

        Element saved = elementRepository.save(element);

        return new ElementResponse(
                saved.getId(),
                saved.getTitre(),
                saved.getDescription(),
                saved.getMiniature(),
                saved.getLien(),
                saved.getType(),
                saved.getChapitre().getId()
                
        );
    }

    // 📄 Get elements by chapitre
    public List<ElementResponse> getElementsByChapitre(Long chapitreId) {
        return elementRepository.findByChapitreId(chapitreId)
                .stream()
                .map(e -> new ElementResponse(
                        e.getId(),
                        e.getTitre(),
                        e.getDescription(),
                        e.getMiniature(),
                        e.getLien(),
                        e.getType(),
                        e.getChapitre().getId()
                ))
                .collect(Collectors.toList());
    }

    // ✏️ Update element
    public ElementResponse updateElement(Long id, ElementRequest request) {
        Element element = elementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Element not found"));

        element.setTitre(request.getTitre());
        element.setLien(request.getLien());
        element.setType(request.getType());

        Element updated = elementRepository.save(element);

        return new ElementResponse(
                updated.getId(),
                updated.getTitre(),
                updated.getDescription(),
                updated.getMiniature(),
                updated.getLien(),
                updated.getType(),
                updated.getChapitre().getId()
        );
    }

    // ❌ Delete element
    public String deleteElement(Long id) {
        Element element = elementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Element not found"));
        elementRepository.delete(element);
        return "Element deleted successfully";
    }
}
