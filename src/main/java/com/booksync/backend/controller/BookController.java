package com.booksync.backend.controller;

import com.booksync.backend.dto.CreateBookRequest;
import com.booksync.backend.model.Book;

import com.booksync.backend.repository.BookRepository;
import com.booksync.backend.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing book operations.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    /**
     * Initializes the book controller with required repository and service.
     * @param bookRepository Repository for book data access
     * @param bookService Service handling book business logic
     */
    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    /**
     * Creates a new book based on the provided request.
     * @param request Contains book details for creation
     * @return ResponseEntity with created book or error message if creation fails
     */
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

    /**
     * Retrieves a specific book by its ID.
     * @param id Book identifier
     * @return ResponseEntity containing the book if found, null otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookRepository.findById(id).orElse(null));
    }

    /**
     * Retrieves all books in the system.
     * @return ResponseEntity containing a list of all books
     */
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    /**
     * Searches for books based on a query string and availability status.
     * @param query Search term to filter books
     * @param available Flag to filter by book availability (defaults to true)
     * @return ResponseEntity containing list of matching books
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam String query,
            @RequestParam(defaultValue = "true") boolean available) {
        List<Book> books = bookService.searchBooks(query, available);
        return ResponseEntity.ok(books);
    }

    /**
     * Deletes a book by its ID.
     * @param id Book identifier
     * @return ResponseEntity with success status if deleted, not found if book doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if(!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}