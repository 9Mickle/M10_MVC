package com.epam.m10_mvc.service;

import com.epam.m10_mvc.dao.EventDAO;
import com.epam.m10_mvc.entity.Event;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventDAO eventDAO;

    public int size() {
        return eventDAO.size();
    }

    public List<Event> findAll() {
        return eventDAO.findAll();
    }

    public Event findById(Long id) {
        return eventDAO.findById(id);
    }

    public Event findByTitle(String title) {
        return eventDAO.findByTitle(title);
    }

    public Event save(Event event) {
        return eventDAO.save(event);
    }

    public Event update(Event event) {
        return eventDAO.update(event);
    }

    public Event delete(Long id) {
        return eventDAO.delete(id);
    }

    public void clear() {
        eventDAO.clear();
    }
}
