package com.booksync.backend.controller;

import com.booksync.backend.model.Book;

import com.booksync.backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("")
    public ResponseEntity<List<Book>> getLoans() {
        return ResponseEntity.ok(bookRepository.findAll());
    }
}