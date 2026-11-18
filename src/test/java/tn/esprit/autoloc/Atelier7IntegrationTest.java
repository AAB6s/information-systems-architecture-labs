package tn.esprit.autoloc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
class Atelier7IntegrationTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper json;
    @Autowired private IContratRepository contrats;
    @Autowired private IMaintenanceRepository maintenances;

    @Test
    void affectationsEtCreationAssocieeSontPersistees() throws Exception {
        long contratsAvant = contrats.count();
        long maintenancesAvant = maintenances.count();
        long agence = create("/agences", "{\"nom\":\"Tunis\",\"ville\":\"Tunis\"}", "idAgence");
        long client1 = create("/clients", "{\"nom\":\"Amina\",\"prenom\":\"Ben Ali\",\"email\":\"amina7@example.com\"}", "idClient");
        long client2 = create("/clients", "{\"nom\":\"Sami\",\"prenom\":\"Trabelsi\",\"email\":\"sami7@example.com\"}", "idClient");
        long equipement = create("/equipements", "{\"libelle\":\"GPS\"}", "idEquipement");
        long vehicule = create("/vehicules", "{\"immatriculation\":\"AT7-001\",\"marque\":\"Renault\",\"modele\":\"Clio\",\"categorie\":\"CITADINE\",\"tarifJournalier\":70,\"statut\":\"DISPONIBLE\",\"agenceId\":" + agence + "}", "idVehicule");
        long reservation = create("/reservations", "{\"dateDebut\":\"2027-01-10\",\"dateFin\":\"2027-01-12\",\"statut\":\"EN_ATTENTE\",\"clientId\":" + client1 + ",\"vehiculeId\":" + vehicule + "}", "idReservation");

        mvc.perform(put("/reservations/{id}/client", reservation).param("nom", "Sami").param("prenom", "Trabelsi"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.clientId").value(client2));
        mvc.perform(get("/reservations/{id}", reservation))
                .andExpect(status().isOk()).andExpect(jsonPath("$.clientId").value(client2));

        mvc.perform(put("/vehicules/{id}/equipements/{equipement}", vehicule, equipement))
                .andExpect(status().isOk()).andExpect(jsonPath("$.equipementIds[0]").value(equipement));
        mvc.perform(post("/vehicules/maintenance").param("description", "Revision").param("immatriculation", "AT7-001"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.statut").value("MAINTENANCE"));
        org.junit.jupiter.api.Assertions.assertEquals(maintenancesAvant + 1, maintenances.count());

        mvc.perform(post("/contrats/affectation").param("dateSignature", "2027-01-01")
                        .param("dateDebut", "2027-01-10").param("dateFin", "2027-01-12"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.reservationId").value(reservation));

        mvc.perform(post("/reservations/avec-contrat").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservation\":{\"dateDebut\":\"2027-02-10\",\"dateFin\":\"2027-02-12\",\"statut\":\"EN_ATTENTE\",\"clientId\":" + client1 + ",\"vehiculeId\":" + vehicule + "},\"dateSignature\":\"2027-02-01\",\"montantTotal\":210,\"valide\":false}"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.contratId").isNumber());
        org.junit.jupiter.api.Assertions.assertEquals(contratsAvant + 2, contrats.count());
    }

    private long create(String path, String body, String idField) throws Exception {
        String response = mvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return json.readTree(response).get(idField).asLong();
    }
}
