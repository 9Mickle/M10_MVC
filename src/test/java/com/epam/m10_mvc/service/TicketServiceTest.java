package com.epam.m10_mvc.service;

import com.epam.m10_mvc.entity.Ticket;
import com.epam.m10_mvc.entity.TicketCategory;
import com.epam.m10_mvc.storage.TicketStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TicketServiceTest {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketStorage ticketStorage;
    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        ticket = new Ticket(1L, 1L, 1L, 1, TicketCategory.STANDARD);
        ticketStorage.saveTicket(ticket);
    }

    @AfterEach
    public void after() {
        ticketStorage.deleteTicket(ticket.getId());
    }

    @Test
    void size() {
        assertEquals(1, ticketService.size());
    }

    @Test
    void findAll() {
        assertEquals(List.of(ticket), ticketService.findAll());
    }

    @Test
    void findById() {
        assertEquals(ticket, ticketService.findById(ticket.getId()));
    }

    @Test
    void save() {
        Ticket newTicket = new Ticket(2L, 1L, 1L, 2, TicketCategory.STANDARD);
        assertEquals(newTicket, ticketService.save(newTicket));
        ticketStorage.deleteTicket(newTicket.getId());
    }

    @Test
    void update() {
        int newPlace = 2;
        Ticket updatedTicket = new Ticket(1L, 1L, 1L, newPlace, TicketCategory.STANDARD);
        assertEquals(updatedTicket, ticketService.update(updatedTicket));
    }

    @Test
    void delete() {
        assertEquals(ticket, ticketService.delete(ticket.getId()));
    }

    @Test
    void clear() {
        ticketService.clear();
        assertEquals(0, ticketStorage.getSizeStorage());
    }
}