package com.epam.m10_mvc.dao;

import com.epam.m10_mvc.entity.Ticket;
import com.epam.m10_mvc.storage.TicketStorage;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Слой доступа к хранилищу билетов.
 */
@Component
@AllArgsConstructor
public class TicketDAO {

    private final TicketStorage ticketStorage;

    public int size() {
        return ticketStorage.getSizeStorage();
    }

    public List<Ticket> findAll() {
        return ticketStorage.getAllTickets();
    }

    public Ticket findById(Long id) {
        return ticketStorage.getTicketById(id);
    }

    public Ticket save(Ticket Ticket) {
        return ticketStorage.saveTicket(Ticket);
    }

    public Ticket update(Ticket Ticket) {
        return ticketStorage.updateTicket(Ticket);
    }

    public Ticket delete(Long id) {
        return ticketStorage.deleteTicket(id);
    }

    public void clear() {
        ticketStorage.deleteAllTickets();
    }
}
