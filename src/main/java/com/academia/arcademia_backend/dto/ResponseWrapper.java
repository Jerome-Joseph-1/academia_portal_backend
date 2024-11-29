package com.academia.arcademia_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWrapper<T> {
    private int statusCode;
    private String message;
    private T data;
}
