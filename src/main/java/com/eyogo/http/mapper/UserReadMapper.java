package com.eyogo.http.mapper;

import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto mapFrom(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .role(object.getRole())
                .birthday(object.getBirthday())
                .image(object.getImage())
                .gender(object.getGender())
                .build();
    }
}
