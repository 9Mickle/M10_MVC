package com.epam.m10_mvc.facade;

import com.epam.m10_mvc.entity.Event;
import com.epam.m10_mvc.entity.Ticket;
import com.epam.m10_mvc.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface BookingFacade {

    /**
     * CRUD Event.
     */
    Event getEventById(Long id);

    Event getEventByTitle(String title);

    List<Event> getEventsByDate(LocalDate date);

    List<Event> getAllEvents();

    Event createEvent(Event event);

    Event updateEvent(Event event);

    Event deleteEvent(Long id);

    /**
     * CRUD User.
     */
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User createUser(User user);

    User updateUser(User user);

    User deleteUser(Long id);

    /**
     * CRUD Ticket
     */
    Ticket getTicketById(Long id);

    Ticket bookTicket(Ticket ticket);

    Ticket updateTicket(Ticket ticket);

    List<Ticket> getALlTickets();

    void preloadTickets();

    List<Ticket> getBookedTicketsByUser(Long userId);

    List<Ticket> getBookedTicketsByEvent(Event event);

    Ticket cancelTicket(Long id);
}
