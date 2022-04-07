package com.epam.m10_mvc.facade;

import com.epam.m10_mvc.entity.Event;
import com.epam.m10_mvc.entity.Ticket;
import com.epam.m10_mvc.entity.TicketCategory;
import com.epam.m10_mvc.entity.User;
import com.epam.m10_mvc.exception.AlreadyExistException;
import com.epam.m10_mvc.jaxb.XMLService;
import com.epam.m10_mvc.service.EventService;
import com.epam.m10_mvc.service.TicketService;
import com.epam.m10_mvc.service.UserService;
import com.epam.m10_mvc.storage.TicketStorage;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookingFacadeImplTest {

    @Autowired
    private BookingFacadeImpl bookingFacade;
    @Autowired
    private TicketStorage ticketStorage;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket(1L, 1L, 1L, 1, TicketCategory.STANDARD);
        ticketStorage.saveTicket(ticket);
    }

    @AfterEach
    void after() {
        ticketStorage.deleteTicket(ticket.getId());
    }

    /**
     * Создать пользователя.
     */
    @Test
    void canCreateUser() {
        User user = new User(1L, "Misha", "misha@mail.ru");
        assertEquals(user, bookingFacade.createUser(user));
    }

    /**
     * Создать мероприятие.
     */
    @Test
    void canCreateEvent() {
        Event event = new Event(1L, "Event", LocalDate.now());
        assertEquals(event, bookingFacade.createEvent(event));
    }

    /**
     * Успешный заказ билета.
     */
    @Test
    void successBookTicket() {
        Ticket newTicket = new Ticket(2L, 1L, 1L, 2, TicketCategory.STANDARD);
        assertEquals(newTicket, bookingFacade.bookTicket(newTicket));
    }

    /**
     * Неуспешный заказ билета.
     */
    @Test
    void throwAlreadyExistException() {
        // Нельзя сохранять с таким же id.
        Ticket ticket1 = new Ticket(1L, 1L, 1L, 2, TicketCategory.STANDARD);
        assertThrows(AlreadyExistException.class, () -> bookingFacade.bookTicket(ticket1));

        // Нельзя заказать билет на тоже место, которое уже занято на это мероприятие.
        Ticket ticket2 = new Ticket(2L, 1L, 1L, 1, TicketCategory.STANDARD);
        assertThrows(AlreadyExistException.class, () -> bookingFacade.bookTicket(ticket2));
        ticketStorage.deleteTicket(ticket2.getId());
    }

    /**
     * Получить все билеты оформленные на пользовтеля.
     */
    @Test
    void getBookedTicketsByUser() {
        assertEquals(List.of(ticket), bookingFacade.getBookedTicketsByUser(1L));
    }

    /**
     * Получить все билеты на это мероприятие.
     */
    @Test
    void getBookedTicketsByEvent() {
        Event event = new Event(1L, "Event", LocalDate.of(2022, 4, 4));
        assertEquals(List.of(ticket), bookingFacade.getBookedTicketsByEvent(event));
    }

    /**
     * Удалить билет.
     */
    @Test
    void cancelTicket() {
        assertEquals(ticket, bookingFacade.cancelTicket(ticket.getId()));
    }
}