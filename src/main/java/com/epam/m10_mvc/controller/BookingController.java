package com.epam.m10_mvc.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        return "redirect:/user/create";
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
}