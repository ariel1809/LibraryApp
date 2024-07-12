package com.example.libraryapp.repository;

import com.example.libraryapp.entity.Loan;
import com.example.libraryapp.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
    Optional<Loan> findByStudentId(String idStudent);
    Optional<Loan> findByStudent(Student student);
}
