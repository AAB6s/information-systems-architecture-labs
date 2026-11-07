package tn.esprit.autoloc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
class DtoValidationIntegrationTest {
    @Autowired private MockMvc mvc;

    @Test
    void creerEtLireDesDtosSansGrapheJpa() throws Exception {
        String agence = mvc.perform(post("/agences")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"Tunis\",\"ville\":\"Tunis\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idAgence").isNumber())
            .andExpect(jsonPath("$.employes").doesNotExist())
            .andReturn().getResponse().getContentAsString();
        Long agenceId = new com.fasterxml.jackson.databind.ObjectMapper()
            .readTree(agence).get("idAgence").asLong();

        String vehicule = mvc.perform(post("/vehicules")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"immatriculation\":\"TP06-001\",\"marque\":\"Renault\","
                    + "\"modele\":\"Clio\",\"categorie\":\"CITADINE\","
                    + "\"tarifJournalier\":75,\"statut\":\"DISPONIBLE\","
                    + "\"agenceId\":" + agenceId + "}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.agenceId").value(agenceId))
            .andExpect(jsonPath("$.agence").doesNotExist())
            .andReturn().getResponse().getContentAsString();
        long vehiculeId = new com.fasterxml.jackson.databind.ObjectMapper()
            .readTree(vehicule).get("idVehicule").asLong();
        mvc.perform(get("/vehicules"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].immatriculation").value("TP06-001"));

        String client = mvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"Amel\",\"email\":\"amel@example.com\"}"))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();
        long clientId = new com.fasterxml.jackson.databind.ObjectMapper()
            .readTree(client).get("idClient").asLong();
        String reservation = mvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dateDebut\":\"2026-11-10\",\"dateFin\":\"2026-11-12\","
                    + "\"statut\":\"EN_ATTENTE\",\"clientId\":" + clientId
                    + ",\"vehiculeId\":" + vehiculeId + "}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.clientId").value(clientId))
            .andReturn().getResponse().getContentAsString();
        long reservationId = new com.fasterxml.jackson.databind.ObjectMapper()
            .readTree(reservation).get("idReservation").asLong();
        mvc.perform(post("/contrats")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dateSignature\":\"2026-11-07\",\"montantTotal\":150,"
                    + "\"valide\":true,\"reservationId\":" + reservationId
                    + ",\"paiements\":[{\"montant\":150,\"datePaiement\":\"2026-11-07\","
                    + "\"modePaiement\":\"CARTE\"}]}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.paiements[0].montant").value(150))
            .andExpect(jsonPath("$.paiements[0].contratId").isNumber())
            .andExpect(jsonPath("$.reservation").doesNotExist());
    }

    @Test
    void validerLesChampsEtCentraliserLesErreurs() throws Exception {
        mvc.perform(post("/agences")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"  \"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.erreurs.nom").exists());
        mvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"Amel\",\"email\":\"invalid\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.erreurs.email").exists());
        mvc.perform(post("/employes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"Sami\",\"role\":\"AGENT\",\"agenceId\":999999}"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.statut").value(404));
        mvc.perform(post("/agences")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"Tunis\",\"idAgence\":5}"))
            .andExpect(status().isBadRequest());
    }
}
