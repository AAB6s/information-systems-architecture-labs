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
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final IClientService service;

    @GetMapping
    public List<Client> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Client findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client entite) {
        Client created = service.create(entite);
        return ResponseEntity.created(URI.create("/api/clients/" + created.getIdClient()))
                .body(created);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable("id") Long id, @RequestBody Client entite) {
        return service.update(id, entite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
