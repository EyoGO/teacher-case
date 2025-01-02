package com.eyogo.http.mapper;

import com.eyogo.http.dto.UserCreateDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.entity.User;
import com.eyogo.http.util.LocalDateFormatter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

public class CreateUserMapper implements Mapper<UserCreateDto, User> {

    public static final String IMAGE_FOLDER = "users";
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(UserCreateDto object) {
        User user = User.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .password(object.getPassword())
                .birthday(LocalDateFormatter.format(object.getBirthday()))
//                .image(IMAGE_FOLDER + File.separator + object.getEmail() + "_" + object.getImage().getName())
                .role(Role.valueOf(object.getRole()))
                .gender(Gender.valueOf(object.getGender()))
                .build();

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
        return user;
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
