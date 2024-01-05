package com.eyogo.http.dto;

import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
//    String id;
    String firstName;
    String lastName;
    String email;
    String password;
    String birthday;
    Part image;
    String role;
    String gender;
}
