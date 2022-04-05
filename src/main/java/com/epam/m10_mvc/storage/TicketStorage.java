package com.epam.m10_mvc.storage;

import com.epam.m10_mvc.entity.Ticket;
import com.epam.m10_mvc.exception.AlreadyExistException;
import com.epam.m10_mvc.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Хранилище билетов.
 */
@Component
@AllArgsConstructor
public class TicketStorage {

    private final Map<Long, Ticket> ticketMap;

    public Integer getSizeStorage() {
        return ticketMap.size();
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(ticketMap.values());
    }

    public Ticket getTicketById(Long id) {
        return ticketMap.values()
                .stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Ticket not found with id: " + id));
    }

    public Ticket saveTicket(Ticket ticket) {
        if (ticketMap.values()
                .stream()
                .noneMatch(elem -> (elem.getId().equals(ticket.getId())
                        ||
                        (elem.getPlace().equals(ticket.getPlace()) && (elem.getEventId().equals(ticket.getEventId())))))) {

            ticketMap.put(ticket.getId(), ticket);
            return ticket;
        } else {
            throw new AlreadyExistException("Ticket with same id or same place already exist!");
        }
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketMap.computeIfPresent(ticket.getId(), (key, value) -> value = ticket);
    }

    public Ticket deleteTicket(Long id) {
        return ticketMap.remove(id);
    }

    public void deleteAllTickets() {
        ticketMap.clear();
    }
}
