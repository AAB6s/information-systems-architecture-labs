package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.web.dto.AgenceRequest;
import tn.esprit.autoloc.web.dto.AgenceResponse;

@Component
public class AgenceMapper {
    public Agence toEntity(AgenceRequest request) {
        Agence entity = new Agence();
        entity.setNom(request.nom());
        entity.setVille(request.ville());
        entity.setAdresse(request.adresse());
        entity.setTelephone(request.telephone());
        return entity;
    }

    public AgenceResponse toResponse(Agence entity) {
        return new AgenceResponse(
            entity.getIdAgence(),
            entity.getNom(),
            entity.getVille(),
            entity.getAdresse(),
            entity.getTelephone()
        );
    }
}
