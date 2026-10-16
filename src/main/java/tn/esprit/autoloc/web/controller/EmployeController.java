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
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {
    private final IEmployeService service;

    @GetMapping
    public List<Employe> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Employe findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Employe> create(@RequestBody Employe entite) {
        Employe created = service.create(entite);
        return ResponseEntity.created(URI.create("/api/employes/" + created.getIdEmploye()))
                .body(created);
    }

    @PutMapping("/{id}")
    public Employe update(@PathVariable("id") Long id, @RequestBody Employe entite) {
        return service.update(id, entite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
