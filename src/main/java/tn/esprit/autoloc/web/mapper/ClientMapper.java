package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientRequest;
import tn.esprit.autoloc.web.dto.ClientResponse;

@Component
public class ClientMapper {
    public Client toEntity(ClientRequest request) {
        Client entity = new Client();
        entity.setNom(request.nom());
        entity.setPrenom(request.prenom());
        entity.setEmail(request.email());
        entity.setTelephone(request.telephone());
        entity.setNumPermis(request.numPermis());
        entity.setDateInscription(request.dateInscription());
        return entity;
    }

    public ClientResponse toResponse(Client entity) {
        return new ClientResponse(
            entity.getIdClient(),
            entity.getNom(),
            entity.getPrenom(),
            entity.getEmail(),
            entity.getTelephone(),
            entity.getNumPermis(),
            entity.getDateInscription()
        );
    }
}
