package com.pcw.assignment.services;

import com.pcw.assignment.models.Book;
import com.pcw.assignment.repositories.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BooksRepository repository;

    public BookService(BooksRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book addBook(Book book) {

        repository.save(book);
        return book;
    }

    public Book getById(int id) {
        return repository.getById(id);
    }

}
