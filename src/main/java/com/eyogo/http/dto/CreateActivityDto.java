package com.eyogo.http.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateActivityDto {
    Integer userId;
    Integer unitId;
    String activityName;
    String description;
    Integer authorId;
}
