package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.web.dto.EmployeRequest;
import tn.esprit.autoloc.web.dto.EmployeResponse;

@Component
public class EmployeMapper {
    public Employe toEntity(EmployeRequest request) {
        Employe entity = new Employe();
        entity.setNom(request.nom());
        entity.setPrenom(request.prenom());
        entity.setRole(request.role());
        Agence agence = new Agence();
        agence.setIdAgence(request.agenceId());
        entity.setAgence(agence);
        return entity;
    }

    public EmployeResponse toResponse(Employe entity) {
        return new EmployeResponse(
            entity.getIdEmploye(),
            entity.getNom(),
            entity.getPrenom(),
            entity.getRole(),
            entity.getAgence() == null ? null : entity.getAgence().getIdAgence()
        );
    }
}
