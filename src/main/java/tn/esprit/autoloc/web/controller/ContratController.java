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
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {
    private final IContratService service;

    @GetMapping
    public List<Contrat> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Contrat findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Contrat> create(@RequestBody Contrat entite) {
        Contrat created = service.create(entite);
        return ResponseEntity.created(URI.create("/api/contrats/" + created.getIdContrat()))
                .body(created);
    }

    @PutMapping("/{id}")
    public Contrat update(@PathVariable("id") Long id, @RequestBody Contrat entite) {
        return service.update(id, entite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
