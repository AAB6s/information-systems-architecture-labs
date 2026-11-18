package tn.esprit.autoloc.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationWithContratRequestDTO(
        @NotNull @Valid ReservationRequest reservation,
        @NotNull LocalDate dateSignature,
        @NotNull @DecimalMin("0.00") BigDecimal montantTotal,
        @NotNull Boolean valide) {
}
