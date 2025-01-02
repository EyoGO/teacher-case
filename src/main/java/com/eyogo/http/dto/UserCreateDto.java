package com.eyogo.http.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

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
    
    MultipartFile image;

    String role;
    String gender;
}
