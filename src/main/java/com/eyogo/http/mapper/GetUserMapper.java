package com.eyogo.http.mapper;

import com.eyogo.http.dto.GetUserDto;
import com.eyogo.http.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserMapper implements Mapper<User, GetUserDto> {

    private static final GetUserMapper INSTANCE = new GetUserMapper();

    @Override
    public GetUserDto mapFrom(User object) {
        return GetUserDto.builder()
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

    public static GetUserMapper getInstance() {
        return INSTANCE;
    }
}
