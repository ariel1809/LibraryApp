package com.example.libraryapp.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class ResponseApi {
    private String message;
    private Object data;
    private Integer code;
}