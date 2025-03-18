package com.eyogo.http.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
public class UserCreateDto {
    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Email
    String email;

    String password;
    String birthday;

    MultipartFile image;

    String role;
    String gender;
}
