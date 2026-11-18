package tn.esprit.autoloc.service.impl;

import java.util.List;
import java.util.HashSet;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.web.mapper.VehiculeMapper;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    @Transactional
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        if (vehicule.getEquipements() == null) {
            vehicule.setEquipements(new HashSet<>());
        }
        vehicule.getEquipements().add(equipement);
        return toDTO(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeDTO affecterMaintenanceAVehicule(String description, String immatriculation) {
        if (description == null || description.isBlank() || immatriculation == null || immatriculation.isBlank()) {
            throw new IllegalArgumentException("La description et l'immatriculation sont obligatoires");
        }
        Vehicule vehicule = vehiculeRepository.findByImmatriculationIgnoreCase(immatriculation.trim())
                .orElseThrow(() -> new IllegalArgumentException("Vehicule introuvable: " + immatriculation));
        Maintenance maintenance = new Maintenance();
        maintenance.setDescription(description.trim());
        maintenance.setDateDebut(LocalDate.now());
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);
        vehicule.setStatut(StatutVehicule.MAINTENANCE);
        return toDTO(vehiculeRepository.save(vehicule));
    }

    private VehiculeDTO toDTO(Vehicule vehicule) {
        var response = vehiculeMapper.toResponse(vehicule);
        return new VehiculeDTO(response.idVehicule(), response.immatriculation(), response.marque(),
                response.modele(), response.categorie(), response.tarifJournalier(), response.statut(),
                response.agenceId(), response.equipementIds());
    }

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        validate(vehicule);
        resolveAssociations(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existing = findById(id);
        validate(vehicule);
        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setCategorie(vehicule.getCategorie());
        existing.setTarifJournalier(vehicule.getTarifJournalier());
        existing.setStatut(vehicule.getStatut());
        resolveAssociations(vehicule);
        existing.setAgence(vehicule.getAgence());
        existing.setEquipements(vehicule.getEquipements());
        return vehiculeRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void validate(Vehicule vehicule) {
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas etre negatif");
        }
    }

    private void resolveAssociations(Vehicule vehicule) {
        Long agenceId = vehicule.getAgence().getIdAgence();
        vehicule.setAgence(agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId)));
        Set<Long> ids = vehicule.getEquipements().stream()
                .map(Equipement::getIdEquipement).collect(Collectors.toSet());
        Set<Equipement> equipements = ids.stream()
                .map(id -> equipementRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Equipement", id)))
                .collect(Collectors.toSet());
        vehicule.setEquipements(equipements);
    }
}
