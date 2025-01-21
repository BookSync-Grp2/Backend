package com.booksync.backend.service;

import com.booksync.backend.dto.CreateBookRequest;
import com.booksync.backend.model.Book;
import com.booksync.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Service class for handling book-related business logic.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    /**
     * Initializes the book service with required repository.
     * @param bookRepository Repository for book data access
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Creates a new book from the provided request data.
     * Sets the book as available by default.
     *
     * @param request DTO containing book creation details
     * @return The created and persisted book entity
     */
    public Book createBook(CreateBookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setISBN(request.getIsbn());
        book.setPublishedYear(request.getPublishedYear());
        book.setAvailable(true);

        return bookRepository.save(book);
    }

    /**
     * Searches for books based on a query string and availability.
     * Returns an empty list if the query is null or empty.
     *
     * @param query Search term to filter books
     * @param availableOnly Whether to only return available books
     * @return List of books matching the search criteria
     */
    public List<Book> searchBooks(String query, boolean availableOnly) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.searchBooks(query.trim(), availableOnly);
    }
}