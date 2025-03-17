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
    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Email
    String email;

//    @NotBlank(groups = CreateAction.class) //TODO: only on create it must be filled, operations like update must not require it
//    @Size(min = 6)//TODO handle exception
    String password;
    String birthday;
    
    MultipartFile image;

    String role;
    String gender;
}
