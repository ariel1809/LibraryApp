package com.example.libraryapp.service.api;

import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Student;
import com.example.libraryapp.utils.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LibraryApi {
    ResponseEntity<ResponseApi> createStudent(Student student);
    ResponseEntity<ResponseApi> createBook(Book book);
    ResponseEntity<ResponseApi> borrowBook(String idBook, String idStudent);
    ResponseEntity<ResponseApi> returnBook(String idBook, String idBorrow);
    ResponseEntity<ResponseApi> getAllBooks(int page, int size);
}
