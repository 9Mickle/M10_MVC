package com.epam.m10_mvc.controller;

import com.epam.m10_mvc.entity.Event;
import com.epam.m10_mvc.entity.Ticket;
import com.epam.m10_mvc.entity.User;
import com.epam.m10_mvc.facade.BookingFacadeImpl;
import com.epam.m10_mvc.service.PdfService;
import com.lowagie.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class BookingController {

    private final BookingFacadeImpl bookingFacade;
    private final PdfService pdfService;

//////////////////////////////////USER ZONE/////////////////////////////////////////////////////

    /**
     * Получить домашнюю страницу.
     */
    @Operation(summary = "Get Home Page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = User.class))
            }),
    })
    @GetMapping("/")
    public String getHomePage(Model model) {
        return "home";
    }

    /**
     * Получить список всех пользователей.
     */
    @Operation(summary = "This is to fetch all the users from storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = User.class))
            }),
    })
    @GetMapping("user/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", bookingFacade.getAllUsers());
        return "users";
    }

    /**
     * Получить форму для создания пользователя.
     */
    @Operation(summary = "Get a form to create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = User.class))
            }),
    })
    @GetMapping("/user/create")
    public String getCreteUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user_create";
    }

    /**
     * Создать пользователя.
     */
    @Operation(summary = "Create a new user and save it to the storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = {
                    @Content(
                            schema = @Schema(implementation = User.class))})
    })
    @PostMapping("/user/create")
    public String createUser(@ModelAttribute("user") User user) {
        bookingFacade.createUser(user);
        return "redirect:/user/all";
    }

    /**
     * Обновить пользователя.
     */
    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = User.class))})
    })
    @PostMapping("/user/update")
    public String updateUser(@RequestBody User user) {
        bookingFacade.updateUser(user);
        return "redirect:/user/all";
    }

    /**
     * Удалить пользователя.
     */
    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = User.class))})
    })
    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        bookingFacade.deleteUser(id);
        return "redirect:/user/all";
    }

//////////////////////////////////TICKET ZONE/////////////////////////////////////////////////////

    /**
     * Получить форму для создания билета.
     */
    @Operation(summary = "Get a form to create a ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Ticket.class))
            }),
    })
    @GetMapping("/ticket/create")
    public String getCreteTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "ticket_create";
    }

    /**
     * Заполнить хранилище данными из xml файла.
     */
    @Operation(summary = "Get a list of tickets that were received from the pdf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Ticket.class))
            }),
    })
    @GetMapping("/ticket/fill")
    public String fillTicketStorageDataFromXML(Model model) {
        bookingFacade.preloadTickets();
        model.addAttribute("tickets", bookingFacade.getALlTickets());
        return "tickets";
    }

    /**
     * Получить список всех билетов в пдф формате.
     */
    @Operation(summary = "Get a list of tickets in pdf format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Ticket.class))
            }),
    })
    @GetMapping("/get/pdf/tickets")
    public ResponseEntity<byte[]> getPDFTickets(Model model) throws IOException, DocumentException {

        //Формирование HTML страницы.
        model.addAttribute("tickets", bookingFacade.getALlTickets());

        //Создание PDF файла из HTML.
        pdfService.createPdf();

        String pathToPDF = "src/main/resources/pdf/tickets.pdf";
        byte[] pdfContent = Files.readAllBytes(Path.of(pathToPDF));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String fileName = "tickets.pdf";
        headers.setContentDispositionFormData(fileName, fileName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    /**
     * Создать билет.
     */
    @Operation(summary = "Create a new ticket and save it to the storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = {
                    @Content(
                            schema = @Schema(implementation = Ticket.class))})
    })
    @PostMapping("/ticket/create")
    public String createTicket(@ModelAttribute("ticket") Ticket ticket) {
        bookingFacade.bookTicket(ticket);
        return "redirect:/ticket/create";
    }

    /**
     * Удалить билет.
     */
    @Operation(summary = "Delete a ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Ticket.class))})
    })
    @PostMapping("/ticket/delete/{id}")
    public String deleteTicket(@PathVariable Long id) {
        bookingFacade.cancelTicket(id);
        return "redirect:/get/pdf/tickets";
    }

//////////////////////////////////EVENT ZONE/////////////////////////////////////////////////////

    /**
     * Получить список всех мероприятий.
     */
    @Operation(summary = "This is to fetch all the events from storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Event.class))
            }),
    })
    @GetMapping("event/all")
    public String getAllEvents(Model model) {
        model.addAttribute("events", bookingFacade.getAllEvents());
        return "events";
    }

    /**
     * Получить список всех мероприятий на определенную дату.
     */
    @Operation(summary = "This is to fetch all the events by date from storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Event.class))
            }),
    })
    @GetMapping("event/all/{date}")
    public String getEventsByDate(Model model, @PathVariable String date) {
        LocalDate ld = LocalDate.parse(date);
        model.addAttribute("events", bookingFacade.getEventsByDate(ld));
        return "events";
    }

    /**
     * Получить список всех мероприятий по названию.
     */
    @Operation(summary = "This is to fetch all the events by title from storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Event.class))
            }),
    })
    @GetMapping("event/title/{title}")
    public String getEventsByTitle(Model model, @PathVariable String title) {
        model.addAttribute("events", bookingFacade.getEventByTitle(title));
        return "events";
    }

    /**
     * Получить форму для создания мероприятия.
     */
    @Operation(summary = "Get a form to create a event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Event.class))
            }),
    })
    @GetMapping("/event/create")
    public String getCreteEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event_create";
    }

    /**
     * Создать мероприятие.
     */
    @Operation(summary = "Create a new event and save it to the storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = {
                    @Content(
                            schema = @Schema(implementation = Event.class))})
    })
    @PostMapping("/event/create")
    public String createEvent(@ModelAttribute("event") Event event) {
        bookingFacade.createEvent(event);
        return "redirect:/event/create";
    }

    /**
     * Обновить мероприятие.
     */
    @Operation(summary = "Update a event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Event.class))})
    })
    @PostMapping("/event/update")
    public String updateEvent(@RequestBody Event event) {
        bookingFacade.updateEvent(event);
        return "redirect:/event/all";
    }

    /**
     * Удалить мероприятие.
     */
    @Operation(summary = "Delete a event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = Event.class))})
    })
    @PostMapping("/event/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        bookingFacade.deleteEvent(id);
        return "redirect:/";
    }
}