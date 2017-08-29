package com.microsoft.azure.pixeltracker.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}