package com.epam.m10_mvc;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class M10MvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(M10MvcApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Create pdf");
//        System.out.println(xmlMarshaller.unmarshalFromXML("src/main/resources/xml/tickets.xml"));
//    }
}
