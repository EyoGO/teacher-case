package com.eyogo.http.validation;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
