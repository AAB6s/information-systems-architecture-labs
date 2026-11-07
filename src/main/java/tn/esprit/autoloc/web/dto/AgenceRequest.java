package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AgenceRequest(
    @NotBlank @Size(max = 120) String nom,
    @Size(max = 120) String ville,
    @Size(max = 255) String adresse,
    @Size(max = 30) String telephone
) {
}
