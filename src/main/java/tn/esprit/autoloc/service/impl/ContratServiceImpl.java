package tn.esprit.autoloc.service.impl;

import java.util.List;
import java.time.LocalDate;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.domain.Reservation;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {
    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;

    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        if (dateSignature == null || dateDebut == null || dateFin == null || dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("Les dates du contrat et de la reservation sont invalides");
        }
        List<Reservation> reservations = reservationRepository.findByDateDebutAndDateFin(dateDebut, dateFin);
        if (reservations.size() != 1) {
            throw new IllegalArgumentException("Les dates doivent identifier une seule reservation");
        }
        Reservation reservation = reservations.get(0);
        if (reservation.getContrat() != null) {
            throw new IllegalArgumentException("Cette reservation possede deja un contrat");
        }
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dateSignature);
        contrat.setMontantTotal(BigDecimal.ZERO);
        contrat.setValide(false);
        contrat.setReservation(reservation);
        contrat = contratRepository.save(contrat);
        return new ContratDTO(contrat.getIdContrat(), contrat.getDateSignature(),
                contrat.getMontantTotal(), contrat.getValide(), reservation.getIdReservation());
    }

    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        validate(contrat);
        contrat.setReservation(reservationRepository.findById(contrat.getReservation().getIdReservation())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", contrat.getReservation().getIdReservation())));
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        Contrat existing = findById(id);
        validate(contrat);
        existing.setDateSignature(contrat.getDateSignature());
        existing.setMontantTotal(contrat.getMontantTotal());
        existing.setValide(contrat.getValide());
        existing.setReservation(reservationRepository.findById(contrat.getReservation().getIdReservation())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", contrat.getReservation().getIdReservation())));
        return contratRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    private void validate(Contrat contrat) {
        if (contrat.getMontantTotal() != null && contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas etre negatif");
        }
    }
}
