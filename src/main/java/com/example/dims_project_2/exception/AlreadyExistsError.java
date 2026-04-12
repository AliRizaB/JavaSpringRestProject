package com.example.dims_project_2.exception;

public class AlreadyExistsError extends RuntimeException{
    public AlreadyExistsError(String message) {
        super(message);
    }
}
