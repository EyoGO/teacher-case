package com.eyogo.http.dto;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class ActivityReadDto {
    Integer id;
    UserReadDto user;
    String name;
    String description;
    UserReadDto author;
}
