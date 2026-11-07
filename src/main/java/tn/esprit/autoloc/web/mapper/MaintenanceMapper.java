package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.MaintenanceRequest;
import tn.esprit.autoloc.web.dto.MaintenanceResponse;

@Component
public class MaintenanceMapper {
    public Maintenance toEntity(MaintenanceRequest request) {
        Maintenance entity = new Maintenance();
        entity.setDateDebut(request.dateDebut());
        entity.setDateFin(request.dateFin());
        entity.setDescription(request.description());
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(request.vehiculeId());
        entity.setVehicule(vehicule);
        return entity;
    }

    public MaintenanceResponse toResponse(Maintenance entity) {
        return new MaintenanceResponse(
            entity.getIdMaintenance(),
            entity.getDateDebut(),
            entity.getDateFin(),
            entity.getDescription(),
            entity.getVehicule() == null ? null : entity.getVehicule().getIdVehicule()
        );
    }
}
