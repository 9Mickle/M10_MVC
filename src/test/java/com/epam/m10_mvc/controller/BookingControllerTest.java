package com.epam.m10_mvc.controller;

import com.epam.m10_mvc.entity.Event;
import com.epam.m10_mvc.entity.Ticket;
import com.epam.m10_mvc.entity.TicketCategory;
import com.epam.m10_mvc.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingController bookingController;

    private static Event event = new Event(1L, "Event", LocalDate.of(2022, 4, 4));
    private static User user = new User(1L, "Misha", "misha@mail.ru");
    private static Ticket ticket = new Ticket(1L, 1L, 1L, 123, TicketCategory.STANDARD);

    @BeforeEach
    public void setUp() {
//        event = new Event(1L, "Event", LocalDate.of(2022, 4, 4));
//        user = new User(1L, "Misha", "misha@mail.ru");
//        ticket = new Ticket(1L, 1L, 1L, 123, TicketCategory.STANDARD);
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
        bookingController.createUser(user);
        User updatedUser = new User(1L, "NewName", "NewEmail");
        mockMvc.perform(post("http://localhost:8080/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedUser))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(post("http://localhost:8080/user/delete/" + user.getId())
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void getCreteTicketForm() throws Exception {
        mockMvc.perform(get("http://localhost:8080/ticket/create"))
                .andDo(print())
                .andExpect(xpath("/html/body/div/form/label[1]").string("Enter id: "))
                .andExpect(xpath("/html/body/div/form/label[2]").string("Enter userId: "))
                .andExpect(xpath("/html/body/div/form/label[3]").string("Enter eventId: "))
                .andExpect(xpath("/html/body/div/form/label[4]").string("Enter place: "))
                .andExpect(xpath("/html/body/div/form/label[5]").string("Enter category (STANDARD OR PREMIUM): "));
    }

    @Test
    void fillTicketStorageDataFromXML() throws Exception {
        mockMvc.perform(get("http://localhost:8080/ticket/fill")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[1]").string("111111"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[2]/td[1]").string("222222"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[3]/td[1]").string("333333"))
                .andReturn();
    }

    @Test
    void getPDFTickets() throws Exception {
        mockMvc.perform(get("http://localhost:8080/get/pdf/tickets")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void createTicket() throws Exception {
        mockMvc.perform(post("http://localhost:8080/ticket/create")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void deleteTicket() throws Exception {
        bookingController.createTicket(ticket);
        mockMvc.perform(post("http://localhost:8080/ticket/delete/" + ticket.getId())
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void getEventsByDate() throws Exception {
        mockMvc.perform(get("http://localhost:8080/event/all/" + event.getDate())
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/table/tbody/tr/td[1]").string("1"))
                .andReturn();
    }

    @Test
    void getEventsByTitle() throws Exception {
        mockMvc.perform(get("http://localhost:8080/event/title/" + event.getTitle())
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/table/tbody/tr/td[1]").string("1"))
                .andReturn();
    }

    @Test
    void getCreteEventForm() throws Exception {
        mockMvc.perform(get("http://localhost:8080/event/create"))
                .andDo(print())
                .andExpect(xpath("/html/body/div/form/label[1]").string("Enter id: "))
                .andExpect(xpath("/html/body/div/form/label[2]").string("Enter title: "))
                .andExpect(xpath("/html/body/div/form/label[3]").string("Enter date: "));
    }

    @Test
    void createEvent() throws Exception {
        bookingController.createEvent(event);
        mockMvc.perform(post("http://localhost:8080/event/create")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void updateEvent() throws Exception {
        bookingController.createEvent(event);
        Event updatedEvent = new Event(1L, "NewName", LocalDate.of(2022, 4, 4));
        mockMvc.perform(post("http://localhost:8080/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedEvent))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}