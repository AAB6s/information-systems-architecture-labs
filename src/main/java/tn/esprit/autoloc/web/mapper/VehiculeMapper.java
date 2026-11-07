package tn.esprit.autoloc.web.mapper;

import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;

@Component
public class VehiculeMapper {
    public Vehicule toEntity(VehiculeRequest request) {
        Vehicule entity = new Vehicule();
        entity.setImmatriculation(request.immatriculation());
        entity.setMarque(request.marque());
        entity.setModele(request.modele());
        entity.setCategorie(request.categorie());
        entity.setTarifJournalier(request.tarifJournalier());
        entity.setStatut(request.statut());
        Agence agence = new Agence();
        agence.setIdAgence(request.agenceId());
        entity.setAgence(agence);
        entity.setEquipements(request.equipementIds() == null ? Collections.emptySet() :
                request.equipementIds().stream().map(id -> {
                    Equipement equipement = new Equipement();
                    equipement.setIdEquipement(id);
                    return equipement;
                }).collect(Collectors.toSet()));
        return entity;
    }

    public VehiculeResponse toResponse(Vehicule entity) {
        return new VehiculeResponse(
            entity.getIdVehicule(),
            entity.getImmatriculation(),
            entity.getMarque(),
            entity.getModele(),
            entity.getCategorie(),
            entity.getTarifJournalier(),
            entity.getStatut(),
            entity.getAgence() == null ? null : entity.getAgence().getIdAgence(),
            entity.getEquipements() == null ? Collections.emptySet() : entity.getEquipements().stream().map(Equipement::getIdEquipement).collect(Collectors.toSet())
        );
    }
}
