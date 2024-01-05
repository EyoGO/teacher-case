package com.eyogo.http.dto;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class GetActivityDto {
    Integer id;
    GetUserDto user;
    String name;
    String description;
    GetUserDto author;
}
