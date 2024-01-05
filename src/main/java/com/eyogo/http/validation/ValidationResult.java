package com.eyogo.http.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import com.eyogo.http.validation.Error;

public class ValidationResult {

    @Getter
    private final List<Error> errors = new ArrayList<>();

    public void addError(Error error) {
        errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
