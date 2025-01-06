package com.booksync.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Server is running!";
    }

    @GetMapping("/api/status")
    public String status() {
        return "API is operational";
    }
}