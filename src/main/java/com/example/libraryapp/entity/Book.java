package com.example.libraryapp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Setter
@Document
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
    private int pages;
    @EqualsAndHashCode.Exclude
    private int price;
    @EqualsAndHashCode.Exclude
    private int nbrCopies;
}
