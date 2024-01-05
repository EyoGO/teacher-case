package com.eyogo.http.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateActivityDto {
    Integer userId;
    String unitId;
    String activityName;
    String description;
    Integer authorId;
}
