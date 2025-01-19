package com.booksync.backend.controller;

import com.booksync.backend.dto.CreateBookRequest;
import com.booksync.backend.model.Book;

import com.booksync.backend.repository.BookRepository;
import com.booksync.backend.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBook(
            @RequestBody CreateBookRequest request) {
        try {
            Book createdBook = bookService.createBook(request);
            return ResponseEntity.ok(createdBook);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookRepository.findById(id).orElse(null));
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getLoans() {
        return ResponseEntity.ok(bookRepository.findAll());
    }


    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam String query,
            @RequestParam(defaultValue = "true") boolean available) {
        List<Book> books = bookService.searchBooks(query, available);
        return ResponseEntity.ok(books);
    }
}