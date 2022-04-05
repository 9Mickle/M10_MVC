package com.epam.m10_mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket {

    @XmlElement(name = "id")
    private Long id;
    @XmlElement(name = "eventId")
    private Long eventId;
    @XmlElement(name = "userId")
    private Long userId;
    @XmlElement(name = "place")
    private Integer place;
    @XmlElement(name = "category")
    private TicketCategory category;
}
