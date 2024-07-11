package com.example.libraryapp.service.impl;

import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Student;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.repository.LoanRepository;
import com.example.libraryapp.repository.StudentRepository;
import com.example.libraryapp.service.api.LibraryApi;
import com.example.libraryapp.utils.CodeEnum;
import com.example.libraryapp.utils.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LibraryImpl implements LibraryApi {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LoanRepository loanRepository;

    private final ResponseApi responseApi = new ResponseApi();

    @Override
    public ResponseEntity<ResponseApi> createStudent(Student student) {
        try {

            if (student == null){
                responseApi.setMessage("Student is null");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            Student savedStudent = new Student();
            savedStudent.setAge(student.getAge());
            savedStudent.setEmail(student.getEmail());
            savedStudent.setName(student.getName());
            savedStudent.setPenalty(0);
            savedStudent.setGender(student.getGender());
            savedStudent.setNbrLoans(0);
            savedStudent.setPhone(student.getPhone());
            savedStudent.setSurname(student.getSurname());
            savedStudent = studentRepository.save(savedStudent);

            responseApi.setMessage("Student created");
            responseApi.setCode(CodeEnum.SUCCESS.getCode());
            responseApi.setData(savedStudent);
            return new ResponseEntity<>(responseApi, HttpStatus.CREATED);

        }catch (Exception e){
            responseApi.setCode(CodeEnum.ERROR.getCode());
            responseApi.setMessage(e.getMessage());
            responseApi.setData(null);

            return new ResponseEntity<>(responseApi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseApi> createBook(Book book) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> borrowBook(String idBook, String idStudent) {
        return null;
    }
}
