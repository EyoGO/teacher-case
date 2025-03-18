package com.eyogo.http.dto;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class ActivityReadDto {
    Integer id;
    Integer user;
    String name;
    String description;
    Integer authorId;
}
