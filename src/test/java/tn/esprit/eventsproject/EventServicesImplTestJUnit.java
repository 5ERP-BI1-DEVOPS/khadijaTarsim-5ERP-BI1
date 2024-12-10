package tn.esprit.eventsproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.eventsproject.EventsProjectApplication;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = {EventsProjectApplication.class})
class EventServicesImplTestJUnit {
    private static final Logger log = LoggerFactory.getLogger(EventServicesImplTestJUnit.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventServicesImpl eventService;

    private Event testEvent;
    private Event savedEvent;

    @BeforeEach
    public void setUp() {
        testEvent = new Event();
        testEvent.setDescription("Test Event");
        testEvent.setDateDebut(LocalDate.now());
        testEvent.setDateFin(LocalDate.now().plusDays(2));
        testEvent.setCout(500.0f);
        testEvent.setParticipants(new HashSet<>());
        testEvent.setLogistics(new HashSet<>());
    }

    @AfterEach
    public void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    void testAddEvent() {
        savedEvent = eventService.addEvent(testEvent);
        Assertions.assertNotNull(savedEvent.getIdEvent());
        Assertions.assertEquals("Test Event", savedEvent.getDescription());
        log.info("Added Event: {} with Description: {}", savedEvent.getIdEvent(), savedEvent.getDescription());
    }

    @Test
    void testRetrieveAllEvents() {
        List<Event> eventList = new ArrayList<Event>() {
            {
                add(new Event(0, "Event 1", LocalDate.now(), LocalDate.now().plusDays(1), 100.0f, new HashSet<>(), new HashSet<>()));
                add(new Event(0, "Event 2", LocalDate.now(), LocalDate.now().plusDays(2), 200.0f, new HashSet<>(), new HashSet<>()));
                add(new Event(0, "Event 3", LocalDate.now(), LocalDate.now().plusDays(3), 300.0f, new HashSet<>(), new HashSet<>()));
            }
        };

        eventRepository.saveAll(eventList);
        List<Event> retrievedList = eventService.retrieveAllEvents();

        Assertions.assertEquals(3, retrievedList.size());
        log.info("Retrieved Events: {}", retrievedList);
    }

    @Test
    void testUpdateEvent() {
        savedEvent = eventService.addEvent(testEvent);
        savedEvent.setDescription("Updated Test Event");
        Event updatedEvent = eventService.updateEvent(savedEvent);

        Assertions.assertEquals("Updated Test Event", updatedEvent.getDescription());
        log.info("Updated Event: {} with Description: {}", updatedEvent.getIdEvent(), updatedEvent.getDescription());
    }

    @Test
    void testDeleteEvent() {
        savedEvent = eventService.addEvent(testEvent);
        eventService.deleteEvent(savedEvent.getIdEvent());
        Assertions.assertFalse(eventRepository.findById(savedEvent.getIdEvent()).isPresent());
        log.info("Deleted Event with ID: {}", savedEvent.getIdEvent());
    }
}
