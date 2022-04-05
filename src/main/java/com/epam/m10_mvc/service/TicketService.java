package com.epam.m10_mvc.service;

import com.epam.m10_mvc.dao.TicketDAO;
import com.epam.m10_mvc.entity.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketDAO ticketDAO;

    public int size() {
        return ticketDAO.size();
    }

    public List<Ticket> findAll() {
        return ticketDAO.findAll();
    }

    public Ticket findById(Long id) {
        return ticketDAO.findById(id);
    }

    public Ticket save(Ticket ticket) {
        return ticketDAO.save(ticket);
    }

    public Ticket update(Ticket ticket) {
        return ticketDAO.update(ticket);
    }

    public Ticket delete(Long id) {
        return ticketDAO.delete(id);
    }

    public void clear() {
        ticketDAO.clear();
    }
}
