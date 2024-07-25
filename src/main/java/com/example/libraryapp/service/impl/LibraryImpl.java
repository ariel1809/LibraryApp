package com.example.libraryapp.service.impl;

import com.example.libraryapp.entity.*;
import com.example.libraryapp.repository.*;
import com.example.libraryapp.service.api.LibraryApi;
import com.example.libraryapp.utils.CodeEnum;
import com.example.libraryapp.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LibraryImpl implements LibraryApi {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanHistoryRepository loanHistoryRepository;
    @Autowired
    private LoanBookRepository loanBookRepository;

    private final ResponseApi responseApi = new ResponseApi();
    private static final Logger logger =  LoggerFactory.getLogger(LibraryImpl.class);

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
            logger.error("An error occurred while returning the book", e);
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
            savedBook.setAmount(book.getAmount());
            savedBook.setRating(book.getRating());
            savedBook.setConsultation(book.getConsultation());
            savedBook.setNbrCopies(book.getNbrCopies());
            savedBook = bookRepository.save(savedBook);

            responseApi.setMessage("Book created");
            responseApi.setCode(CodeEnum.SUCCESS.getCode());
            responseApi.setData(savedBook);
            return new ResponseEntity<>(responseApi, HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
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

            LoanBook loanBook = new LoanBook();
            student.setNbrLoans(student.getNbrLoans() + 1);
            student = studentRepository.save(student);
            Loan loan1 = loanRepository.findByStudent(student).orElse(null);
            if (loan1 != null && loan1.getStudent().equals(student)){
                System.out.println("ok");
                loanBook.setBook(book);
                loanBook.setLoanDate(LocalDate.now());
                loanBook.setReturnDate(LocalDate.now().plusDays(15));
                loanBook = loanBookRepository.save(loanBook);
                loan1.getLoanBooks().add(loanBook);
                loan1 = loanRepository.save(loan1);
                responseApi.setMessage("Student borrowed");
                responseApi.setCode(CodeEnum.SUCCESS.getCode());
                responseApi.setData(loan1);
                return new ResponseEntity<>(responseApi, HttpStatus.CREATED);
            }
            LoanHistory loanHistory = new LoanHistory();
            loanBook.setBook(book);
            loanBook.setLoanDate(LocalDate.now());
            loanBook.setReturnDate(LocalDate.now().plusDays(15));
            loanBook = loanBookRepository.save(loanBook);
            loanHistory.setLoanBook(loanBook);
            loanHistory = loanHistoryRepository.save(loanHistory);
            Loan loan = new Loan();
            loan.setStudent(student);
            loanBook.setBook(book);
            loanBook.setLoanDate(LocalDate.now());
            loanBook.setReturnDate(LocalDate.now().plusDays(15));
            loanBook = loanBookRepository.save(loanBook);
            loan.getLoanBooks().add(loanBook);
            loan.getLoanHistories().add(loanHistory);
            loan = loanRepository.save(loan);
            responseApi.setMessage("Student borrowed");
            responseApi.setCode(CodeEnum.SUCCESS.getCode());
            responseApi.setData(loan);
            return new ResponseEntity<>(responseApi, HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            responseApi.setCode(CodeEnum.ERROR.getCode());
            responseApi.setMessage(e.getMessage());
            responseApi.setData(null);
            return new ResponseEntity<>(responseApi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseApi> returnBook(String idLoanBook, String idBorrow) {
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
            if (idLoanBook == null){
                responseApi.setMessage("Loan book not found");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            LoanBook loanBook = loanBookRepository.findById(idLoanBook).orElse(null);
            if (loanBook == null){
                responseApi.setMessage("Loan book is null");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            Student student = loan.getStudent();
            if (loanBook.getReturnDate().isBefore(LocalDate.now())){
                student.setPenalty((int) ChronoUnit.DAYS.between(loanBook.getReturnDate(), LocalDate.now()));
            }
            LoanHistory loanHistory = loanHistoryRepository.findByLoanBook(loanBook).orElse(null);
            if (loanHistory != null){
                System.out.println("ok");
                loanHistory.setReturnDate(LocalDate.now());
                loanHistoryRepository.save(loanHistory);
            }
            loan.getLoanBooks().remove(loanBook);
            Book book = loanBook.getBook();
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
            e.printStackTrace();
            responseApi.setCode(CodeEnum.ERROR.getCode());
            responseApi.setMessage(e.getMessage());
            responseApi.setData(null);
            return new ResponseEntity<>(responseApi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseApi> getAllBooks() {
        try {

            List<Book> books = bookRepository.findAll();
            if (books.isEmpty()){
                responseApi.setMessage("No books found");
                responseApi.setCode(CodeEnum.NULL.getCode());
                responseApi.setData(null);
                return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
            }
            responseApi.setMessage("Books found");
            responseApi.setCode(CodeEnum.SUCCESS.getCode());
            responseApi.setData(books);
            return new ResponseEntity<>(responseApi, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            responseApi.setCode(CodeEnum.ERROR.getCode());
            responseApi.setMessage(e.getMessage());
            responseApi.setData(null);
            return new ResponseEntity<>(responseApi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
