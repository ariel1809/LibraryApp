package com.example.libraryapp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "loans")
public class Loan {
    @EqualsAndHashCode.Include
    private String id;
    @EqualsAndHashCode.Exclude
    @DBRef
    private Student student;
    @EqualsAndHashCode.Exclude
    @DBRef
    private List<Book> books;
    @EqualsAndHashCode.Exclude
    private LocalDate startDate;
    @EqualsAndHashCode.Exclude
    private LocalDate endDate;
}
