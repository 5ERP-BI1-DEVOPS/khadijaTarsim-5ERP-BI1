package tn.esprit.eventsproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventServicesTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private EventServicesImpl eventServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddParticipant() {
        Participant participant = new Participant(1, "tarsim", "khadija", Tache.INVITE, null);
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        Participant result = eventServices.addParticipant(participant);

        assertEquals("Doe", result.getNom());
        verify(participantRepository, times(1)).save(participant);
    }

    @Test
    void testAddAffectEventParticipant() {
        Event event = new Event();
        event.setIdEvent(1);

        Participant participant = new Participant();
        participant.setIdPart(1);

        when(participantRepository.findById(1)).thenReturn(Optional.of(participant));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventServices.addAffectEvenParticipant(event, 1);

        assertEquals(1, result.getIdEvent());
        verify(participantRepository, times(1)).findById(1);
        verify(eventRepository, times(1)).save(event);
    }
}
