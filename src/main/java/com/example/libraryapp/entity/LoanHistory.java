package com.example.libraryapp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@Document(collection = "loan_histories")
@EqualsAndHashCode
public class LoanHistory {
    @EqualsAndHashCode.Include
    private String id;
    @EqualsAndHashCode.Exclude
    @DBRef
    private LoanBook loanBook;
    @EqualsAndHashCode.Exclude
    private LocalDate returnDate;
}
