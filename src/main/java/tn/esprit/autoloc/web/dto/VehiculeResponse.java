package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.util.Set;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

public record VehiculeResponse(
    Long idVehicule,
    String immatriculation,
    String marque,
    String modele,
    CategorieVehicule categorie,
    BigDecimal tarifJournalier,
    StatutVehicule statut,
    Long agenceId,
    Set<Long> equipementIds
) {
}
