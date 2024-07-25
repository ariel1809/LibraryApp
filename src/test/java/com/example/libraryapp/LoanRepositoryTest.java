package com.example.libraryapp;

import com.example.libraryapp.entity.Loan;
import com.example.libraryapp.entity.Student;
import com.example.libraryapp.repository.LoanRepository;
import com.example.libraryapp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.Optional;

@DataMongoTest
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testFindByStudentId() {
        String studentId = "669029bfa3c68f5af252b0b5"; // Remplacez par un ID existant dans votre base de données
        Student student = studentRepository.findById(studentId).orElse(null);
        Optional<Loan> loan = loanRepository.findByStudent(student);
        System.out.println(loan.get());
    }
}