package com.epam.m10_mvc.jaxb;

import com.epam.m10_mvc.entity.Ticket;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@XmlRootElement(name = "storage")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketContainer {

    @XmlElementWrapper(name = "tickets")
    @XmlElement(name = "ticket", type = Ticket.class)
    private final List<Ticket> tickets;

    public TicketContainer() {
        this.tickets = new ArrayList<>();
    }
}

