package com.epam.m10_mvc.facade;

import com.epam.m10_mvc.entity.Event;
import com.epam.m10_mvc.entity.Ticket;
import com.epam.m10_mvc.entity.User;
import com.epam.m10_mvc.jaxb.TicketContainer;
import com.epam.m10_mvc.jaxb.XMLService;
import com.epam.m10_mvc.service.EventService;
import com.epam.m10_mvc.service.TicketService;
import com.epam.m10_mvc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingFacadeImpl implements BookingFacade {

    private final UserService userService;
    private final EventService eventService;
    private final TicketService ticketService;
    private final XMLService xmlService;

    /**
     * Methods for Event.
     */

    @Override
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        return eventService.findById(id);
    }

    @Override
    public Event getEventByTitle(String title) {
        return eventService.findByTitle(title);
    }

    @Override
    public List<Event> getEventsByDate(LocalDate date) {
        return eventService.findAll()
                .stream()
                .filter(elem -> elem.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public Event createEvent(Event event) {
        return eventService.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventService.update(event);
    }

    @Override
    public Event deleteEvent(Long id) {
        return eventService.delete(id);
    }

    /**
     * Methods for User.
     */
    @Override
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userService.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        return userService.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userService.update(user);
    }

    @Override
    public User deleteUser(Long id) {
        return userService.delete(id);
    }

    /**
     * Methods for Ticket.
     */
    @Override
    public Ticket getTicketById(Long id) {
        return ticketService.findById(id);
    }

    @Override
    public void preloadTickets() {
        final String pathToFile = "src/main/resources/xml/tickets.xml";
        try {
            TicketContainer ticketContainer = xmlService.XMLToObj(pathToFile);
            ticketContainer
                    .getTickets()
                    .forEach(ticketService::save);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket bookTicket(Ticket ticket) {
        return ticketService.save(ticket);
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        return ticketService.update(ticket);
    }

    @Override
    public List<Ticket> getALlTickets() {
        return ticketService.findAll();
    }

    @Override
    public List<Ticket> getBookedTicketsByUser(Long userId) {
        return ticketService.findAll()
                .stream()
                .filter(elem -> elem.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTicketsByEvent(Event event) {
        return ticketService.findAll()
                .stream()
                .filter(elem -> elem.getEventId().equals(event.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Ticket cancelTicket(Long id) {
        return ticketService.delete(id);
    }
}
