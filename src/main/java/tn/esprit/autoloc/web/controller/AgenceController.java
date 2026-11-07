package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import tn.esprit.autoloc.web.dto.AgenceRequest;
import tn.esprit.autoloc.web.dto.AgenceResponse;
import tn.esprit.autoloc.web.mapper.AgenceMapper;
import tn.esprit.autoloc.service.IAgenceService;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {
    private final IAgenceService service;
    private final AgenceMapper mapper;

    @Operation(summary = "Lister les agences")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<AgenceResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @Operation(summary = "Consulter une agence")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ressource trouvée"),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public AgenceResponse findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @Operation(summary = "Créer une agence")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ressource créée"),
        @ApiResponse(responseCode = "400", description = "Identifiant fourni ou règle métier non respectée")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceResponse create(@Valid @RequestBody AgenceRequest demande) {
        return mapper.toResponse(service.create(mapper.toEntity(demande)));
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ressource modifiée"),
        @ApiResponse(responseCode = "400", description = "Règle métier non respectée"),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @PutMapping("/{id}")
    public AgenceResponse update(@Parameter(description = "Identifiant") @PathVariable("id") Long id,
            @Valid @RequestBody AgenceRequest demande) {
        return mapper.toResponse(service.update(id, mapper.toEntity(demande)));
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ressource supprimée, sans corps de réponse"),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
