package com.eyogo.http.mapper;

import com.eyogo.http.dto.CreateActivityDto;
import com.eyogo.http.entity.Activity;

public class CreateActivityMapper implements Mapper<CreateActivityDto, Activity> {

    public static final String IMAGE_FOLDER = "users";
    private static final CreateActivityMapper INSTANCE = new CreateActivityMapper();

    @Override
    public Activity mapFrom(CreateActivityDto object) {
        return Activity.builder()
                .userId(object.getUserId())
//                .unit(object.getUnitId() != null ? Integer.valueOf(object.getUnitId()) : null)
                .activityName(object.getActivityName())
                .description(object.getDescription())
                .authorId(object.getAuthorId())
                .build();
    }

    public static CreateActivityMapper getInstance() {
        return INSTANCE;
    }
}
