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

@WebMvcTest(EventRestController.class) // Specify the controller to test
public class EventRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // To convert objects to JSON

    @Test
    public void testAddParticipant() throws Exception {
        // Create a participant for the test
        Participant participant = new Participant();
        participant.setNom("tarsim");
        participant.setPrenom("khadija");
        participant.setTache(Tache.INVITE);

        // Convert the participant to JSON
        String participantJson = objectMapper.writeValueAsString(participant);

        // Execute the POST request and validate the response
        mockMvc.perform(post("/event/addPart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantJson))
                .andExpect(status().isOk()) // Expect a 200 OK status
                .andExpect(jsonPath("$.nom").value("tarsim")) // Validate the returned `nom`
                .andExpect(jsonPath("$.prenom").value("khadija")) // Validate the returned `prenom`
                .andExpect(jsonPath("$.tache").value("INVITE")); // Validate the returned `tache`
    }
}
