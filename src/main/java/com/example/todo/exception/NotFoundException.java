package com.example.todo.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String massage) {
        super(massage);
    }

}
