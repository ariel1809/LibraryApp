package com.example.libraryapp.controller;

import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Student;
import com.example.libraryapp.service.impl.LibraryImpl;
import com.example.libraryapp.utils.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*")
public class LibraryController {
    @Autowired
    private LibraryImpl library;
    private static final String MAX_SIZE = "10";

    @PostMapping("create-student")
    public ResponseEntity<ResponseApi> createStudent(@RequestBody Student student) {
        return library.createStudent(student);
    }

    @PostMapping("create-book")
    public ResponseEntity<ResponseApi> createBook(@RequestBody Book book) {
        return library.createBook(book);
    }

    @PostMapping("borrow-book")
    public ResponseEntity<ResponseApi> borrowBook(@RequestParam String bookId, @RequestParam String studentId){
        return library.borrowBook(bookId, studentId);
    }

    @PostMapping("return-book")
    public ResponseEntity<ResponseApi> returnBook(@RequestParam String bookId, @RequestParam String studentId){
        return library.returnBook(bookId, studentId);
    }

    @PostMapping("list-book")
    public ResponseEntity<ResponseApi> listBook(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = MAX_SIZE) int size){
        return library.getAllBooks(page, size);
    }

    @PostMapping("list-loan-book")
    public ResponseEntity<ResponseApi> listLoanBook(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = MAX_SIZE) int size,
                                                    @RequestParam String idStudent){
        return library.listReturnedBooksByStudent(page, size, idStudent);
    }

    @PostMapping("list-all-student")
    public ResponseEntity<ResponseApi> listAllStudent(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = MAX_SIZE) int size){
        return library.getAllStudents(page, size);
    }
}
