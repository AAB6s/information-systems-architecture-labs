package tn.esprit.autoloc.web.controller;

import java.util.List;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {
    private final IAgenceService service;

    @GetMapping
    public List<Agence> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Agence findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Agence> create(@RequestBody Agence entite) {
        Agence created = service.create(entite);
        return ResponseEntity.created(URI.create("/api/agences/" + created.getIdAgence()))
                .body(created);
    }

    @PutMapping("/{id}")
    public Agence update(@PathVariable("id") Long id, @RequestBody Agence entite) {
        return service.update(id, entite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
