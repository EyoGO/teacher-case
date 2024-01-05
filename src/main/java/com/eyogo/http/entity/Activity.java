package com.eyogo.http.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {

    private Integer id;
    private Integer userId;
    private Integer unitId;
    private String activityName;
    private String description;
    private Integer authorId;

}

