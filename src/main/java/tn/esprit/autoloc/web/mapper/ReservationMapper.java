package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.ReservationRequest;
import tn.esprit.autoloc.web.dto.ReservationResponse;

@Component
public class ReservationMapper {
    public Reservation toEntity(ReservationRequest request) {
        Reservation entity = new Reservation();
        entity.setDateDebut(request.dateDebut());
        entity.setDateFin(request.dateFin());
        entity.setStatut(request.statut());
        Client client = new Client();
        client.setIdClient(request.clientId());
        entity.setClient(client);
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(request.vehiculeId());
        entity.setVehicule(vehicule);
        return entity;
    }

    public ReservationResponse toResponse(Reservation entity) {
        return new ReservationResponse(
            entity.getIdReservation(),
            entity.getDateDebut(),
            entity.getDateFin(),
            entity.getStatut(),
            entity.getClient() == null ? null : entity.getClient().getIdClient(),
            entity.getVehicule() == null ? null : entity.getVehicule().getIdVehicule()
        );
    }
}
