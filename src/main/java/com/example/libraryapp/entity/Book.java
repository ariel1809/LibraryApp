package com.example.libraryapp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "books")
public class Book {
    @EqualsAndHashCode.Include
    private String id;
    @EqualsAndHashCode.Exclude
    private String title;
    @EqualsAndHashCode.Exclude
    private String author;
    @EqualsAndHashCode.Exclude
    private String isbn;
    @EqualsAndHashCode.Exclude
    private Integer pages;
    @EqualsAndHashCode.Exclude
    private Integer price;
    @EqualsAndHashCode.Exclude
    private Integer nbrCopies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
