package com.epam.m10_mvc.service;

import com.epam.m10_mvc.entity.Event;
import com.epam.m10_mvc.storage.EventStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventStorage eventStorage;

    private Event event;

    @BeforeEach
    public void setUp() {
        event = new Event(1L, "Event", LocalDate.of(2022, 4, 4));
        eventStorage.saveEvent(event);
    }

    @AfterEach
    public void after() {
        eventStorage.deleteEvent(event.getId());
    }

    @Test
    void size() {
        assertEquals(1, eventService.size());
    }

    @Test
    void findAll() {
        assertEquals(List.of(event), eventService.findAll());
    }

    @Test
    void findById() {
        assertEquals(event, eventService.findById(event.getId()));
    }

    @Test
    void findByTitle() {
        assertEquals(event, eventService.findByTitle(event.getTitle()));
    }

    @Test
    void save() {
        Event newEvent = new Event(2L, "NewEvent", LocalDate.now());
        assertEquals(newEvent, eventService.save(newEvent));
        eventStorage.deleteEvent(newEvent.getId());
    }

    @Test
    void update() {
        String newTitle = "New Title";
        Event updatedEvent = new Event(1L, newTitle, LocalDate.now());
        assertEquals(updatedEvent, eventService.update(updatedEvent));
    }

    @Test
    void delete() {
        assertEquals(event, eventService.delete(event.getId()));
    }

    @Test
    void clear() {
        eventService.clear();
        assertEquals(0, eventStorage.getSizeStorage());
    }
}