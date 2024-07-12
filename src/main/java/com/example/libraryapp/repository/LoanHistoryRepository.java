package com.example.libraryapp.repository;

import com.example.libraryapp.entity.LoanBook;
import com.example.libraryapp.entity.LoanHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanHistoryRepository extends MongoRepository<LoanHistory, String> {
    Optional<LoanHistory> findByLoanBook(LoanBook loanBook);
}
