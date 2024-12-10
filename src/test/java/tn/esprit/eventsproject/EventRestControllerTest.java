package tn.esprit.eventsproject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.eventsproject.controllers.EventRestController;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EventRestController.class) // Spécifiez le contrôleur testé
public class EventRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Pour convertir les objets en JSON

    @Test
    public void testAddParticipant() throws Exception {
        // Créez un participant pour le test
        Participant participant = new Participant();
        participant.setNom("Doe");
        participant.setPrenom("John");
        participant.setTache(Tache.INVITE);

        // Convertir le participant en JSON
        String participantJson = objectMapper.writeValueAsString(participant);

        // Exécuter la requête POST et valider la réponse
        mockMvc.perform(post("/event/addPart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("tarsim"))
                .andExpect(jsonPath("$.prenom").value("khadija"))
                .andExpect(jsonPath("$.tache").value("INVITE"));
    }
}
