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
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {
    private final IEquipementService service;

    @GetMapping
    public List<Equipement> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Equipement findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Equipement> create(@RequestBody Equipement entite) {
        Equipement created = service.create(entite);
        return ResponseEntity.created(URI.create("/api/equipements/" + created.getIdEquipement()))
                .body(created);
    }

    @PutMapping("/{id}")
    public Equipement update(@PathVariable("id") Long id, @RequestBody Equipement entite) {
        return service.update(id, entite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
