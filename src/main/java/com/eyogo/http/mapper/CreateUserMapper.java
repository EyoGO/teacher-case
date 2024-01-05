package com.eyogo.http.mapper;

import com.eyogo.http.dto.CreateUserDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.entity.User;
import com.eyogo.http.util.LocalDateFormatter;

import java.io.File;

public class CreateUserMapper implements Mapper<CreateUserDto, User> {

    public static final String IMAGE_FOLDER = "users";
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .password(object.getPassword())
                .birthday(LocalDateFormatter.format(object.getBirthday()))
                .image(IMAGE_FOLDER + File.separator + object.getEmail() + "_" + object.getImage().getSubmittedFileName())
                .role(Role.valueOf(object.getRole()))
                .gender(Gender.valueOf(object.getGender()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
