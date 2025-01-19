package com.booksync.backend.service;

import com.booksync.backend.dto.CreateBookRequest;
import com.booksync.backend.model.Book;
import com.booksync.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(CreateBookRequest request) {
        System.out.println(request.getIsbn());

        if (bookRepository.existsByISBN(request.getIsbn())) {
            throw new IllegalStateException("Book with ISBN " + request.getIsbn() + " already exists");
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setISBN(request.getIsbn());
        book.setCoverUrl(request.getCoverUrl());
        book.setPublishedYear(request.getPublishedYear());
        book.setAvailable(true);

        System.out.println("Book created: " + book);

        return bookRepository.save(book);
    }

    public List<Book> searchBooks(String query, boolean availableOnly) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.searchBooks(query.trim(), availableOnly);
    }
}