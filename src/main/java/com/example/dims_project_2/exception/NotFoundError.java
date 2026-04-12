package com.example.dims_project_2.exception;

public class NotFoundError extends RuntimeException{
    public NotFoundError(String message) {
        super(message);
    }
}
