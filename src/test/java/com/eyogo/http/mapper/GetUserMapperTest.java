package com.eyogo.http.mapper;

import com.eyogo.http.dto.GetUserDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GetUserMapperTest {

    private final GetUserMapper mapper = GetUserMapper.getInstance();

    @Test
    void mapFrom() {
        User user = new User(1, "Bob", "Harvey", "bharvey@gmail.com",
                "Welcome123", Role.ADMIN, LocalDate.of(1999, 4, 13), null, Gender.MALE);

        GetUserDto actualResult = mapper.mapFrom(user);

        GetUserDto expectedResult = GetUserDto.builder()
                .id(1)
                .firstName("Bob")
                .lastName("Harvey")
                .email("bharvey@gmail.com")
                .role(Role.ADMIN)
                .birthday(LocalDate.of(1999, 4, 13))
                .gender(Gender.MALE)
                .build();
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }
}