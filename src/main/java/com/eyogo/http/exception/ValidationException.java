package com.eyogo.http.exception;

import lombok.Getter;

import java.util.List;

import com.eyogo.http.validation.Error;

public class ValidationException extends RuntimeException {

    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
