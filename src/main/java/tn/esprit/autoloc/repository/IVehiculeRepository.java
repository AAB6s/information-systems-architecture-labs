package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import tn.esprit.autoloc.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
    Optional<Vehicule> findByImmatriculationIgnoreCase(String immatriculation);
}
