package com.eyogo.http.integration.database.repository;

import com.eyogo.http.repository.UserRepository;
import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.dto.GetUserDto2;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.entity.User;
import com.eyogo.http.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void classProjection() {
        List<UserReadDto> allByRole = userRepository.findAllByRole(Role.ADMIN, UserReadDto.class);
        assertNotNull(allByRole);
        Assertions.assertThat(allByRole).hasSize(1);
    }

    @Test
    void interfaceProjection() {
        List<GetUserDto2> allByRole = userRepository.findAllByRole(Role.ADMIN);
        assertNotNull(allByRole);
        Assertions.assertThat(allByRole).hasSize(1);
    }

    @Test
//    @Commit
    void checkAuditing() {
        User user = userRepository.findById(1).get();
        user.setGender(Gender.MALE);
        userRepository.flush();
        System.out.println();
    }
}