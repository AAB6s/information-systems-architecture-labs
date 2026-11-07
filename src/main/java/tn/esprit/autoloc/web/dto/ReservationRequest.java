package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;

public record ReservationRequest(
    @NotNull LocalDate dateDebut,
    @NotNull LocalDate dateFin,
    @NotNull StatutReservation statut,
    @NotNull @Positive Long clientId,
    @NotNull @Positive Long vehiculeId
) {
}
