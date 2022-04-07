package com.epam.m10_mvc.controller;

import com.epam.m10_mvc.entity.Event;
import com.epam.m10_mvc.entity.Ticket;
import com.epam.m10_mvc.entity.TicketCategory;
import com.epam.m10_mvc.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingController bookingController;

    private Event event;
    private User user;
    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        event = new Event(1L, "Event", LocalDate.of(2022, 4, 4));
        user = new User(1L, "Misha", "misha@mail.ru");
        ticket = new Ticket(1L, 1L, 1L, 1, TicketCategory.STANDARD);

        bookingController.createEvent(event);
        bookingController.createUser(user);
        bookingController.createTicket(ticket);
    }

    @AfterEach
    public void after() {
        bookingController.deleteEvent(event.getId());
        bookingController.deleteUser(user.getId());
        bookingController.deleteTicket(ticket.getId());
    }

    @Test
    void getHomePage() throws Exception {
        mockMvc.perform(get("http://localhost:8080/"))
                .andDo(print())
                .andExpect(xpath("/html/body/div/h1").string("Welcome to ticket and user service"));
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("http://localhost:8080/user/all"))
                .andDo(print())
                .andExpect(xpath("/html/body/div/table/thead/tr/th[1]").string("Id"))
                .andExpect(xpath("/html/body/div/table/thead/tr/th[2]").string("Name"))
                .andExpect(xpath("/html/body/div/table/thead/tr/th[3]").string("Email"));
    }

    @Test
    void getCreteUserForm() throws Exception {
        mockMvc.perform(get("http://localhost:8080/user/create"))
                .andDo(print())
                .andExpect(xpath("/html/body/div/form/label[1]").string("Enter id: "))
                .andExpect(xpath("/html/body/div/form/label[2]").string("Enter name: "))
                .andExpect(xpath("/html/body/div/form/label[3]").string("Enter email: "));
    }

    @Test
    void createUser() throws Exception {
        mockMvc.perform(post("http://localhost:8080/user/create")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void updateUser() throws Exception {
        User updatedUser = new User(1L, "NewName", "NewEmail");
        mockMvc.perform(post("http://localhost:8080/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedUser))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(xpath("/html/body/div/table/tbody/tr/td[1]").string(String.valueOf(user.getId())))
                .andExpect(xpath("/html/body/div/table/tbody/tr/td[2]").string("NewName"))
                .andReturn();
    }

    @Test
    void deleteUser() {

    }

    @Test
    void getCreteTicketForm() {
    }

    @Test
    void fillTicketStorageDataFromXML() {
    }

    @Test
    void getPDFTickets() {
    }

    @Test
    void createTicket() {
    }

    @Test
    void deleteTicket() {
    }

    @Test
    void getEventsByDate() {
    }

    @Test
    void getEventsByTitle() {
    }

    @Test
    void getCreteEventForm() {
    }

    @Test
    void createEvent() {
    }

    @Test
    void updateEvent() {
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}