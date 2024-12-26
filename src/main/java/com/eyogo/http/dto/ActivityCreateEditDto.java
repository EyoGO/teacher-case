package com.eyogo.http.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ActivityCreateEditDto {
    Integer userId;
    Integer unitId;
    String activityName;
    String description;
    Integer authorId;
}
