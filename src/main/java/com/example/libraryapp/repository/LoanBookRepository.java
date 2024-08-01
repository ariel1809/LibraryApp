package com.example.libraryapp.repository;

import com.example.libraryapp.entity.LoanBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanBookRepository extends MongoRepository<LoanBook, String> {
}