package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ClientRequest(
    @NotBlank @Size(max = 120) String nom,
    @Size(max = 120) String prenom,
    @NotBlank @Email @Size(max = 255) String email,
    @Size(max = 30) String telephone,
    @Size(max = 60) String numPermis,
    LocalDate dateInscription
) {
}
