package tn.esprit.autoloc.web.mapper;

import java.util.Collections;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ContratRequest;
import tn.esprit.autoloc.web.dto.ContratResponse;
import tn.esprit.autoloc.web.dto.PaiementResponse;

@Component
public class ContratMapper {
    public Contrat toEntity(ContratRequest request) {
        Contrat entity = new Contrat();
        entity.setDateSignature(request.dateSignature());
        entity.setMontantTotal(request.montantTotal());
        entity.setValide(request.valide());
        Reservation reservation = new Reservation();
        reservation.setIdReservation(request.reservationId());
        entity.setReservation(reservation);
        if (request.paiements() != null) {
            entity.setPaiements(request.paiements().stream().map(item -> {
                Paiement paiement = new Paiement();
                paiement.setMontant(item.montant());
                paiement.setDatePaiement(item.datePaiement());
                paiement.setModePaiement(item.modePaiement());
                paiement.setContrat(entity);
                return paiement;
            }).toList());
        }
        return entity;
    }

    public ContratResponse toResponse(Contrat entity) {
        return new ContratResponse(
            entity.getIdContrat(),
            entity.getDateSignature(),
            entity.getMontantTotal(),
            entity.getValide(),
            entity.getReservation() == null ? null : entity.getReservation().getIdReservation(),
            entity.getPaiements() == null ? Collections.emptyList() : entity.getPaiements().stream().map(p -> new PaiementResponse(p.getIdPaiement(), p.getMontant(), p.getDatePaiement(), p.getModePaiement(), entity.getIdContrat())).toList()
        );
    }
}
