package com.example.libraryapp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@ToString
@Getter
@Setter
@Document(collection = "loan_books")
public class LoanBook {
    @EqualsAndHashCode.Include
    @Id
    private String id;
    @EqualsAndHashCode.Exclude
    @DBRef
    private Book book;
    @EqualsAndHashCode.Exclude
    private LocalDate loanDate;
    @EqualsAndHashCode.Exclude
    private LocalDate returnDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanBook loanBook = (LoanBook) o;
        return Objects.equals(id, loanBook.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
