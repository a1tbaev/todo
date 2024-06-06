package com.example.todo.dto;

import org.springframework.http.HttpStatus;

public record SimpleResponse(
    String message,
    HttpStatus status
) {
}
