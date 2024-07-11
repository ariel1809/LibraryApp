package com.example.libraryapp.controller;

import com.example.libraryapp.entity.Student;
import com.example.libraryapp.service.impl.LibraryImpl;
import com.example.libraryapp.utils.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LibraryController {
    @Autowired
    private LibraryImpl library;

    @PostMapping("create-student")
    public ResponseEntity<ResponseApi> createStudent(@RequestBody Student student) {
        return library.createStudent(student);
    }
}
