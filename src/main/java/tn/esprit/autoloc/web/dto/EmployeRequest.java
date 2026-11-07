package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import tn.esprit.autoloc.domain.RoleEmploye;

public record EmployeRequest(
    @NotBlank @Size(max = 120) String nom,
    @Size(max = 120) String prenom,
    @NotNull RoleEmploye role,
    @NotNull @Positive Long agenceId
) {
}
