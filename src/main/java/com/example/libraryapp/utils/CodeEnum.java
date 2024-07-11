package com.example.libraryapp.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    SUCCESS(200),
    ERROR(500),
    NULL(201);
    private final int code;
}
