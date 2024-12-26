package com.eyogo.http.mapper;

import com.eyogo.http.dto.UserCreateDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.entity.User;
import com.eyogo.http.util.LocalDateFormatter;

public class CreateUserMapper implements Mapper<UserCreateDto, User> {

    public static final String IMAGE_FOLDER = "users";
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .password(object.getPassword())
                .birthday(LocalDateFormatter.format(object.getBirthday()))
//                .image(IMAGE_FOLDER + File.separator + object.getEmail() + "_" + object.getImage().getName()) //TODO images processing
                .role(Role.valueOf(object.getRole()))
                .gender(Gender.valueOf(object.getGender()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
