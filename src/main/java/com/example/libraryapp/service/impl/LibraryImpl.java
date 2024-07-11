package com.example.libraryapp.service.impl;

import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Loan;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
        try {

            if (book == null){
                responseApi.setMessage("Book is null");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }

            Book savedBook = new Book();
            savedBook.setAuthor(book.getAuthor());
            savedBook.setTitle(book.getTitle());
            savedBook.setIsbn(book.getIsbn());
            savedBook.setPrice(book.getPrice());
            savedBook.setPages(book.getPages());
            savedBook.setNbrCopies(book.getNbrCopies());
            savedBook = bookRepository.save(savedBook);

            responseApi.setMessage("Book created");
            responseApi.setCode(CodeEnum.SUCCESS.getCode());
            responseApi.setData(savedBook);
            return new ResponseEntity<>(responseApi, HttpStatus.CREATED);

        }catch (Exception e){
            responseApi.setCode(CodeEnum.ERROR.getCode());
            responseApi.setMessage(e.getMessage());
            responseApi.setData(null);

            return new ResponseEntity<>(responseApi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseApi> borrowBook(String idBook, String idStudent) {
        try {

            if (idStudent == null){
                responseApi.setMessage("Student not found");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            Student student = studentRepository.findById(idStudent).orElse(null);
            if (student == null){
                responseApi.setMessage("Student not found");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            if (student.getNbrLoans() > 3){
                responseApi.setMessage("Student has more than 03 loans");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            if (idBook == null){
                responseApi.setMessage("Book not found");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            Book book = bookRepository.findById(idBook).orElse(null);
            if (book == null){
                responseApi.setMessage("Book is null");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            if (book.getNbrCopies() == 0){
                responseApi.setMessage("Book has no copies");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            book.setNbrCopies(book.getNbrCopies() - 1);
            book = bookRepository.save(book);
            List<Book> books = new ArrayList<>();
            books.add(book);

            student.setNbrLoans(student.getNbrLoans() + 1);
            student = studentRepository.save(student);
            Loan loan = new Loan();
            loan.setStudent(student);
            loan.setBooks(books);
            loan.setStartDate(LocalDate.now());
            loan.setEndDate(LocalDate.now().plusDays(15));
            loan = loanRepository.save(loan);
            responseApi.setMessage("Student borrowed");
            responseApi.setCode(CodeEnum.SUCCESS.getCode());
            responseApi.setData(loan);
            return new ResponseEntity<>(responseApi, HttpStatus.CREATED);

        }catch (Exception e){
            responseApi.setCode(CodeEnum.ERROR.getCode());
            responseApi.setMessage(e.getMessage());
            responseApi.setData(null);
            return new ResponseEntity<>(responseApi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseApi> returnBook(String idBook, String idBorrow) {
        try {

            if (idBorrow == null){
                responseApi.setMessage("Borrow not found");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            Loan loan = loanRepository.findById(idBorrow).orElse(null);
            if (loan == null){
                responseApi.setMessage("Borrow is null");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            Student student = loan.getStudent();
            if (loan.getEndDate().isBefore(LocalDate.now())){
                student.setPenalty((int) ChronoUnit.DAYS.between(loan.getEndDate(), LocalDate.now()));
            }
            if (idBook == null){
                responseApi.setMessage("Book not found");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            Book book = bookRepository.findById(idBook).orElse(null);
            if (book == null){
                responseApi.setMessage("Book is null");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            loan.getBooks().remove(book);
            book.setNbrCopies(book.getNbrCopies() + 1);
            bookRepository.save(book);
            student.setNbrLoans(student.getNbrLoans() - 1);
            studentRepository.save(student);
            loan = loanRepository.save(loan);
            responseApi.setMessage("Student returned");
            responseApi.setCode(CodeEnum.SUCCESS.getCode());
            responseApi.setData(loan);
            return new ResponseEntity<>(responseApi, HttpStatus.CREATED);

        }catch (Exception e){
            responseApi.setCode(CodeEnum.ERROR.getCode());
            responseApi.setMessage(e.getMessage());
            responseApi.setData(null);
            return new ResponseEntity<>(responseApi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
