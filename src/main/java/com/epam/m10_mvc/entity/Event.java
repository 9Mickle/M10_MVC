package com.epam.m10_mvc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Long id;
    private String title;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
}
