package com.example.libraryapp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Document
public class Student {
    @EqualsAndHashCode.Include
    @Id
    private String id;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private String surname;
    @EqualsAndHashCode.Exclude
    private int age;
    @EqualsAndHashCode.Exclude
    private String gender;
    @EqualsAndHashCode.Exclude
    private String phone;
    @EqualsAndHashCode.Exclude
    private String email;
    @EqualsAndHashCode.Exclude
    private Integer nbrLoans;
    @EqualsAndHashCode.Exclude
    private Integer penalty;
}