package com.eyogo.http.validation;

import com.eyogo.http.dto.UserCreateDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.util.LocalDateFormatter;

public class CreateUserValidator implements Validator<UserCreateDto> {

    private static CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(UserCreateDto object) {
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
