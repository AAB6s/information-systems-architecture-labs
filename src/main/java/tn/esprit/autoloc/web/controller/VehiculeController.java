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
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {
    private final IVehiculeService service;

    @GetMapping
    public List<Vehicule> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Vehicule> create(@RequestBody Vehicule entite) {
        Vehicule created = service.create(entite);
        return ResponseEntity.created(URI.create("/api/vehicules/" + created.getIdVehicule()))
                .body(created);
    }

    @PutMapping("/{id}")
    public Vehicule update(@PathVariable("id") Long id, @RequestBody Vehicule entite) {
        return service.update(id, entite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
