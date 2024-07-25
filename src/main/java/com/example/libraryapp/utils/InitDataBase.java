package com.example.libraryapp.utils;

import com.example.libraryapp.entity.Book;
import com.example.libraryapp.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class InitDataBase {
    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    @Transactional
    public void addNbrCopiesBook() {
        List<Book> books = bookRepository.findAll();
        books.stream()
                .filter(book -> book.getNbrCopies() == null)
                .forEach(book -> {
                    book.setNbrCopies(10);
                    // Log for debugging
                    System.out.println("Updating book: " + book.getId());
                });
        bookRepository.saveAll(books);
    }
}
