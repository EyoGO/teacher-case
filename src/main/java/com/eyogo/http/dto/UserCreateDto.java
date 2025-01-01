package com.eyogo.http.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreateDto {
//    String id;
    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Email
    String email;

    @NotBlank
    @Size(min = 6)
    String password;
    String birthday;
//    Part image; // TODO image processing
    String role;
    String gender;
}
