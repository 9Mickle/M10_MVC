package com.epam.m10_mvc.service;

import com.epam.m10_mvc.facade.BookingFacadeImpl;
import com.lowagie.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@AllArgsConstructor
public class PdfService {

    private final TemplateEngine templateEngine;
    private final BookingFacadeImpl bookingFacade;

    public void createPdf() {
        Context context = new Context();
        context.setVariable("tickets", bookingFacade.getALlTickets());
        String processHtml = templateEngine.process("tickets", context);

        try (OutputStream outputStream = new FileOutputStream("src/main/resources/pdf/tickets.pdf")) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processHtml);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
