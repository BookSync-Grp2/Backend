package com.booksync.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    /**
     * REST controller providing basic server status endpoints.
     */
    @GetMapping("/")
    public String home() {
        return "Server is running!";
    }

    /**
     * Health check endpoint for API status.
     * @return String confirming API operational status
     */
    @GetMapping("/api/status")
    public String status() {
        return "API is operational";
    }
}