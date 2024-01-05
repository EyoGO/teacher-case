package com.eyogo.http.validation;

import com.eyogo.http.dto.CreateUserDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.util.LocalDateFormatter;
import com.eyogo.http.validation.Error;

public class CreateUserValidator implements Validator<CreateUserDto> {

    private static CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(object.getBirthday())) {
            validationResult.addError(Error.of("invalid.birthday", "Birthday is invalid."));
        }
        //TODO throws exception
        if (object.getGender() == null || Gender.valueOf(object.getGender()) == null) {//TODO throws exception if not existing passed
            validationResult.addError(Error.of("invalid.gender", "Gender is invalid."));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
