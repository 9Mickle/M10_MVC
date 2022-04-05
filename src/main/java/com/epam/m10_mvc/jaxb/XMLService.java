package com.epam.m10_mvc.jaxb;

import com.epam.m10_mvc.entity.Ticket;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.nio.file.Path;

@Service
@NoArgsConstructor
public class XMLService {

    private JAXBContext context;

    @PostConstruct
    private void init() throws JAXBException {
        context = JAXBContext.newInstance(TicketContainer.class);
    }

    public TicketContainer XMLToObj(String path) throws JAXBException {
        TicketContainer unmarshal = (TicketContainer) context.createUnmarshaller()
                .unmarshal(Path.of(path).toFile());
        System.out.println(unmarshal);
        return unmarshal;
    }
}
