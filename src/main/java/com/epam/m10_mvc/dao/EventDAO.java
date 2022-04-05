package com.epam.m10_mvc.dao;

import com.epam.m10_mvc.entity.Event;
import com.epam.m10_mvc.storage.EventStorage;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Слой доступа к хранилищу событий.
 */
@Component
@AllArgsConstructor
public class EventDAO {

    private final EventStorage eventStorage;

    public int size() {
        return eventStorage.getSizeStorage();
    }

    public List<Event> findAll() {
        return eventStorage.getAllEvents();
    }

    public Event findById(Long id) {
        return eventStorage.getEventById(id);
    }

    public Event findByTitle(String title) {
        return eventStorage.getEventByTitle(title);
    }

    public Event save(Event event) {
        return eventStorage.saveEvent(event);
    }

    public Event update(Event event) {
        return eventStorage.updateEvent(event);
    }

    public Event delete(Long id) {
        return eventStorage.deleteEvent(id);
    }

    public void clear() {
        eventStorage.deleteAllEvents();
    }
}
