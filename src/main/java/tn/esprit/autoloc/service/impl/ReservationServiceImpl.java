package tn.esprit.autoloc.service.impl;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;
import tn.esprit.autoloc.web.mapper.ReservationMapper;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        if (nom == null || nom.isBlank() || prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException("Le nom et le prenom sont obligatoires");
        }
        List<Client> clients = clientRepository.findByNomIgnoreCaseAndPrenomIgnoreCase(nom.trim(), prenom.trim());
        if (clients.size() != 1) {
            throw new IllegalArgumentException("Le nom et le prenom doivent identifier un seul client");
        }
        Reservation reservation = findById(idReservation);
        reservation.setClient(clients.get(0));
        return toDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.reservation() == null || requestDTO.dateSignature() == null
                || requestDTO.montantTotal() == null || requestDTO.valide() == null) {
            throw new IllegalArgumentException("Les donnees de reservation et de contrat sont obligatoires");
        }
        if (requestDTO.montantTotal().signum() < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas etre negatif");
        }
        Reservation reservation = create(reservationMapper.toEntity(requestDTO.reservation()));
        Contrat contrat = new Contrat();
        contrat.setDateSignature(requestDTO.dateSignature());
        contrat.setMontantTotal(requestDTO.montantTotal());
        contrat.setValide(requestDTO.valide());
        contrat.setReservation(reservation);
        contratRepository.save(contrat);
        reservation.setContrat(contrat);
        return toDTO(reservation);
    }

    private ReservationResponseDTO toDTO(Reservation reservation) {
        return new ReservationResponseDTO(reservation.getIdReservation(), reservation.getDateDebut(),
                reservation.getDateFin(), reservation.getStatut(),
                reservation.getClient() == null ? null : reservation.getClient().getIdClient(),
                reservation.getVehicule() == null ? null : reservation.getVehicule().getIdVehicule(),
                reservation.getContrat() == null ? null : reservation.getContrat().getIdContrat());
    }

    @Override
    public Reservation create(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException("Une nouvelle reservation ne doit pas avoir d'identifiant");
        }
        validate(reservation);
        reservation.setClient(clientRepository.findById(reservation.getClient().getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client", reservation.getClient().getIdClient())));
        reservation.setVehicule(vehiculeRepository.findById(reservation.getVehicule().getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", reservation.getVehicule().getIdVehicule())));
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        Reservation existing = findById(id);
        validate(reservation);
        existing.setDateDebut(reservation.getDateDebut());
        existing.setDateFin(reservation.getDateFin());
        existing.setStatut(reservation.getStatut());
        existing.setClient(clientRepository.findById(reservation.getClient().getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client", reservation.getClient().getIdClient())));
        existing.setVehicule(vehiculeRepository.findById(reservation.getVehicule().getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", reservation.getVehicule().getIdVehicule())));
        return reservationRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private void validate(Reservation reservation) {
        if (reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException("La fin ne peut pas preceder le debut de la reservation");
        }
    }
}
