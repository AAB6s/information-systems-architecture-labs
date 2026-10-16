package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {
    private final IPaiementService service;

    @GetMapping
    public List<Paiement> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Paiement findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}
